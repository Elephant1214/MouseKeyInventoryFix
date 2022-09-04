package nathan.mousekeyinventoryfix.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.gui.screen.Screen.hasControlDown;

@Mixin(HandledScreen.class)
public abstract class MixinHandledScreen {
    protected MinecraftClient client = MinecraftClient.getInstance();
    @Shadow
    @Nullable
    protected Slot focusedSlot;

    /**
     * @author Elephant_1214
     * @reason Fixes dropping items and closing inventories with mouse key binds
     */
    @Inject(method = "onMouseClick(I)V", at = @At(value = "HEAD"))
    public void mouseKeyBindMixin(int button, CallbackInfo ci) {
        System.out.println(button);
        if (this.client.options.inventoryKey.matchesMouse(button)) {
            this.close();
            return;
        } else if (this.focusedSlot != null && this.focusedSlot.hasStack() && this.client.options.dropKey.matchesMouse(button)) {
            this.onMouseClick(this.focusedSlot, this.focusedSlot.id, hasControlDown() ? 1 : 0, SlotActionType.THROW);
            return;
        }
    }

    @Shadow
    protected abstract void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType);

    @Shadow
    public abstract void close();
}
