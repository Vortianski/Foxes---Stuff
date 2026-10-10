package xox.labvorty.foxes_and_stuff.data.events;

import net.minecraft.client.renderer.entity.FoxRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import xox.labvorty.foxes_and_stuff.FoxesStuffMod;
import xox.labvorty.foxes_and_stuff.data.holder.ModdedFoxVariants;
import xox.labvorty.foxes_and_stuff.init.FoxesStuffItems;
import xox.labvorty.foxes_and_stuff.inventory.FoxInventoryMenu;
import xox.labvorty.foxes_and_stuff.items.FoxSpeciesRoll;
import xox.labvorty.foxes_and_stuff.mixin_helpers.TameableFox;
import xox.labvorty.foxes_and_stuff.model.FoxFluffModel;
import xox.labvorty.foxes_and_stuff.renderer.FoxArmorLayer;
import xox.labvorty.foxes_and_stuff.renderer.FoxCollarLayer;
import xox.labvorty.foxes_and_stuff.renderer.FoxFluffLayer;

@EventBusSubscriber(modid = FoxesStuffMod.MOD_ID)
public class FoxEvents {
    public static final TagKey<Item> FOX_TAMING_ITEMS = TagKey.create(Registries.ITEM, FoxesStuffMod.location("fox_taming_items"));

    @SubscribeEvent
    public static void onInteract(PlayerInteractEvent.EntityInteract event) {
        if (!(event.getTarget() instanceof Fox fox) || !(fox instanceof TameableFox tf)) return;
        if (event.getHand() != InteractionHand.MAIN_HAND) return;

        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();
        boolean client = fox.level().isClientSide;

        if (!tf.foxesstuff$isFoxTame()) {
            if (stack.is(FOX_TAMING_ITEMS) && !fox.isBaby()) {
                if (!client) {
                    stack.consume(1, player);
                    ServerLevel level = (ServerLevel) fox.level();
                    if (fox.getRandom().nextInt(3) == 0) {
                        tf.foxesstuff$tameFox(player);
                        level.sendParticles(ParticleTypes.HEART, fox.getX(), fox.getY(0.7), fox.getZ(), 7, 0.4, 0.4, 0.4, 0.02);
                    } else {
                        level.sendParticles(ParticleTypes.SMOKE, fox.getX(), fox.getY(0.7), fox.getZ(), 7, 0.4, 0.4, 0.4, 0.02);
                    }
                }

                finish(event);
            }

            return;
        }

        if (stack.getItem() instanceof FoxSpeciesRoll) {
            if (!client) {
                handleCustomFood(fox, tf, player, stack);
            }

            finish(event);
            return;
        }

        var food = stack.get(DataComponents.FOOD);
        if (food != null && fox.getHealth() < fox.getMaxHealth()) {
            if (!client) {
                fox.heal(Math.max(1, food.nutrition()));
                fox.playSound(fox.getEatingSound(stack), 1.0F, 1.0F);
                stack.consume(1, player);
                ((ServerLevel) fox.level()).sendParticles(ParticleTypes.HEART,
                        fox.getX(), fox.getY(0.7), fox.getZ(), 3, 0.3, 0.3, 0.3, 0.02);
            }
            finish(event);
            return;
        }

        if (!tf.foxesstuff$isFoxOwnedBy(player)) return;

        if (stack.getItem() instanceof DyeItem dye) {
            DyeColor color = dye.getDyeColor();
            if (color != tf.foxesstuff$getCollarColor()) {
                if (!client) {
                    tf.foxesstuff$setCollarColor(color);
                    stack.consume(1, player);
                }
                finish(event);
            }
            return;
        }

        if (stack.isEmpty()) {
            if (!client) {
                if (player.isShiftKeyDown()) {
                    tf.foxesstuff$setFoxOrderedToSit(!tf.foxesstuff$isFoxOrderedToSit());
                } else if (player instanceof ServerPlayer sp) {
                    sp.openMenu(new FoxInventoryMenu.Provider(fox), buf -> buf.writeInt(fox.getId()));
                }
            }
            finish(event);
        }
    }

    private static void handleCustomFood(Fox fox, TameableFox tameableFox, Player player, ItemStack stack) {
        fox.playSound(fox.getEatingSound(stack), 1.0F, 1.0F);
        stack.consume(1, player);

        RandomSource randomSource = player.getRandom();
        if (randomSource.nextBoolean()) {
            String variant = ModdedFoxVariants.getRandomVariant(randomSource);
            if (variant != null) {
                tameableFox.foxesstuff$setModdedVariant(variant);
            }
        } else {
            tameableFox.foxesstuff$setModdedVariant("");
            fox.setVariant(randomSource.nextBoolean() ? Fox.Type.RED : Fox.Type.SNOW);
        }
    }

    private static void finish(PlayerInteractEvent.EntityInteract event) {
        event.setCancellationResult(InteractionResult.SUCCESS);
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void registerLayers(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(FoxFluffModel.LAYER_LOCATION, FoxFluffModel::createBodyLayer);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        if (event.getRenderer(EntityType.FOX) instanceof FoxRenderer renderer) {
            renderer.addLayer(new FoxCollarLayer(renderer));
            renderer.addLayer(new FoxFluffLayer(renderer));
            renderer.addLayer(new FoxArmorLayer(renderer));
        }
    }

    @SubscribeEvent
    public static void onAddCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.insertAfter(
                    Items.DIAMOND_HORSE_ARMOR.getDefaultInstance(),
                    FoxesStuffItems.LEATHER_FOX_ARMOR.get().getDefaultInstance(),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );
            event.insertAfter(
                    FoxesStuffItems.LEATHER_FOX_ARMOR.get().getDefaultInstance(),
                    FoxesStuffItems.IRON_FOX_ARMOR.get().getDefaultInstance(),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );
            event.insertAfter(
                    FoxesStuffItems.IRON_FOX_ARMOR.get().getDefaultInstance(),
                    FoxesStuffItems.GOLD_FOX_ARMOR.get().getDefaultInstance(),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );
            event.insertAfter(
                    FoxesStuffItems.GOLD_FOX_ARMOR.get().getDefaultInstance(),
                    FoxesStuffItems.DIAMOND_FOX_ARMOR.get().getDefaultInstance(),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );
            event.insertAfter(
                    FoxesStuffItems.DIAMOND_FOX_ARMOR.get().getDefaultInstance(),
                    FoxesStuffItems.NETHERITE_FOX_ARMOR.get().getDefaultInstance(),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );
        }

        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.insertAfter(
                    Items.SWEET_BERRIES.getDefaultInstance(),
                    FoxesStuffItems.GOLDEN_BERRIES.get().getDefaultInstance(),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );
        }
    }
}