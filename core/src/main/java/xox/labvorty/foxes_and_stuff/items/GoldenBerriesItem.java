package xox.labvorty.foxes_and_stuff.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GoldenBerriesItem extends Item implements FoxSpeciesRoll {
    public GoldenBerriesItem() {
        super(
                new Properties()
                        .food(
                                new FoodProperties.Builder()
                                        .alwaysEdible()
                                        .nutrition(4)
                                        .saturationModifier(1.2f)
                                        .build()
                        )
        );
    }

    @Override
    public void appendHoverText(
            @NotNull ItemStack itemStack,
            @NotNull TooltipContext tooltipContext,
            @NotNull List<Component> tooltipComponents,
            @NotNull TooltipFlag tooltipFlag
    ) {
        tooltipComponents.add(Component.translatable("tooltip.foxesstuff.fox_species_roll").withStyle(style -> style.withColor(ChatFormatting.GRAY)));
    }
}
