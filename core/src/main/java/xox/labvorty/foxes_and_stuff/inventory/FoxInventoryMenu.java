package xox.labvorty.foxes_and_stuff.inventory;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.fox.Fox;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xox.labvorty.foxes_and_stuff.init.FoxesStuffMenus;
import xox.labvorty.foxes_and_stuff.items.armor.FoxArmorItem;
import xox.labvorty.foxes_and_stuff.mixin_helpers.TameableFox;

public class FoxInventoryMenu extends AbstractContainerMenu {
    private final Container foxInventory;
    @Nullable
    private final Fox fox;

    public FoxInventoryMenu(int containerId, Inventory playerInventory, Fox fox) {
        this(containerId, playerInventory, ((TameableFox) fox).foxesstuff$getFoxInventory(), fox);
    }

    private FoxInventoryMenu(int containerId, Inventory playerInventory, SimpleContainer dummy) {
        this(containerId, playerInventory, dummy, null);
    }

    private FoxInventoryMenu(int containerId, Inventory playerInventory, Container container, @Nullable Fox fox) {
        super(FoxesStuffMenus.FOX_INVENTORY.get(), containerId);
        this.fox = fox;
        this.foxInventory = container;
        this.foxInventory.startOpen(playerInventory.player);

        this.addSlot(new Slot(this.foxInventory, 0, 8, 18) {
            @Override
            public boolean mayPlace(@NotNull ItemStack itemStack) {
                return itemStack.getItem() instanceof FoxArmorItem;
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
        this.addSlot(new Slot(this.foxInventory, 1, 8, 36) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    public static FoxInventoryMenu createClientMenu(int containerId, Inventory playerInventory, FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        Entity entity = playerInventory.player.level().getEntity(entityId);
        if (entity instanceof Fox fox) {
            return new FoxInventoryMenu(containerId, playerInventory, fox);
        }
        return new FoxInventoryMenu(containerId, playerInventory, new SimpleContainer(2));
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return this.fox == null || (this.fox.isAlive() && this.fox.distanceTo(player) < 8.0F);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            result = slotStack.copy();
            int foxSlots = 2;
            int totalSlots = foxSlots + 36;

            if (index < foxSlots) {
                if (!this.moveItemStackTo(slotStack, foxSlots, totalSlots, true)) return ItemStack.EMPTY;
            } else if (TameableFox.foxesstuff$isValidArmor(slotStack)) {
                if (!this.moveItemStackTo(slotStack, 0, 1, false)) return ItemStack.EMPTY;
            } else if (!this.moveItemStackTo(slotStack, 1, 2, false)) {
                return ItemStack.EMPTY;
            }

            if (slotStack.isEmpty()) slot.set(ItemStack.EMPTY); else slot.setChanged();
        }
        return result;
    }

    @Override
    public void removed(@NotNull Player player) {
        super.removed(player);
        this.foxInventory.stopOpen(player);
    }

    @Nullable
    public Fox getFox() { return this.fox; }

    public static class Provider implements MenuProvider {
        private final Fox fox;
        public Provider(Fox fox) { this.fox = fox; }

        @Override
        public @NotNull Component getDisplayName() {
            return fox.hasCustomName() ? fox.getCustomName() : Component.translatable("gui.foxesstuff.fox_inventory");
        }

        @Override
        public AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory, @NotNull Player player) {
            return new FoxInventoryMenu(containerId, playerInventory, fox);
        }
    }
}
