package nathan.mousekeyinventoryfix.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

import static net.minecraft.client.gui.screen.Screen.hasControlDown;

@Mixin(ContainerScreen.class)
public abstract class MixinContainerScreen {
    protected Minecraft minecraft = Minecraft.getInstance();
    @Shadow
    @Nullable
    protected Slot hoveredSlot;

    /**
     * @author Elephant_1214
     * @reason Fixes dropping items and closing inventories with mouse key binds
     */
    @Inject(method = "checkHotbarMouseClicked", at = @At(value = "HEAD"))
    public void mouseKeyBindMixin(int button, CallbackInfo ci) {
        if (this.minecraft.options.keyInventory.matchesMouse(button)) {
            this.onClose();
            return;
        } else if (this.hoveredSlot != null && this.hoveredSlot.hasItem() && this.minecraft.options.keyDrop.matchesMouse(button)) {
            this.slotClicked(this.hoveredSlot, this.hoveredSlot.index, hasControlDown() ? 1 : 0, ClickType.THROW);
            return;
        }
    }

    @Shadow
    protected abstract void slotClicked(Slot slot, int slotIndex, int button, ClickType clickType);

    @Shadow
    public abstract void onClose();
}
