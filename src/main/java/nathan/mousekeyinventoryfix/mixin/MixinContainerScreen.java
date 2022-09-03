package nathan.mousekeyinventoryfix.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

import static net.minecraft.client.gui.screens.Screen.hasControlDown;

@Mixin(AbstractContainerScreen.class)
public abstract class MixinContainerScreen {
    protected Minecraft minecraft = Minecraft.getInstance();
    @Shadow
    @Nullable
    protected Slot hoveredSlot;

    /**
     * @author Elephant_1214
     * @reason Fixes not being able to use mouse key binds to close inventories
     */
    @Inject(method = "checkHotbarMouseClicked", at = @At(value = "HEAD"))
    public void mouseCloseInventoryMixin(int button, CallbackInfo ci) {
        if (this.minecraft.options.keyInventory.matchesMouse(button)) {
            this.onClose();
            return;
        }
    }

    /**
     * @author Elephant_1214
     * @reason Fixes not being able to use mouse key binds to drop items
     */
    @Inject(method = "checkHotbarMouseClicked", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Options;keySwapOffhand:Lnet/minecraft/client/KeyMapping;", opcode = Opcodes.GETFIELD))
    public void mouseDropMixin(int button, CallbackInfo ci) {
        if (this.minecraft.options.keyDrop.matchesMouse(button)) {
            this.slotClicked(this.hoveredSlot, this.hoveredSlot.index, hasControlDown() ? 1 : 0, ClickType.THROW);
            return;
        }
    }

    @Shadow
    protected abstract void slotClicked(Slot slot, int slotIndex, int button, ClickType clickType);

    @Shadow
    public abstract void onClose();
}
