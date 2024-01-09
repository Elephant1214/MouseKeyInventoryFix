package me.elephant1214.mousekeyinventoryfix.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

import static net.minecraft.client.gui.screens.Screen.hasControlDown;

@Mixin(AbstractContainerScreen.class)
public abstract class MixinAbstractContainerScreen {
    @Unique
    protected final Minecraft mouseKeyInventoryFix$minecraft = Minecraft.getInstance();
    @Shadow
    @Nullable
    protected Slot hoveredSlot;

    @Inject(method = "checkHotbarMouseClicked", at = @At(value = "HEAD"), cancellable = true)
    public void handleMouseClick(int button, CallbackInfo ci) {
        if (this.mouseKeyInventoryFix$minecraft.options.keyInventory.matchesMouse(button)) {
            this.onClose();
            ci.cancel();
        } else if (this.hoveredSlot != null && this.hoveredSlot.hasItem() && this.mouseKeyInventoryFix$minecraft.options.keyDrop.matchesMouse(button)) {
            this.slotClicked(this.hoveredSlot, this.hoveredSlot.index, hasControlDown() ? 1 : 0, ClickType.THROW);
            ci.cancel();
        }
    }

    @Shadow
    protected abstract void slotClicked(Slot slot, int slotIndex, int button, ClickType clickType);

    @Shadow
    public abstract void onClose();
}
