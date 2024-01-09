package me.elephant1214.mousekeyinventoryfix.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.gui.screen.Screen.hasControlDown;

@Mixin(HandledScreen.class)
public abstract class MixinHandledScreen {
    @Unique
    private final MinecraftClient mousekeyinventoryfix$minecraftClient = MinecraftClient.getInstance();
    @Shadow
    protected @Nullable Slot focusedSlot;

    @Inject(method = "method_30107", at = @At(value = "HEAD"), cancellable = true)
    public void handleMouseClick(int button, CallbackInfo ci) {
        if (this.mousekeyinventoryfix$minecraftClient.options.keyInventory.matchesMouse(button)) {
            this.onClose();
            ci.cancel();
        } else if (this.focusedSlot != null && this.focusedSlot.hasStack() && this.mousekeyinventoryfix$minecraftClient.options.keyDrop.matchesMouse(button)) {
            this.onMouseClick(this.focusedSlot, this.focusedSlot.id, hasControlDown() ? 1 : 0, SlotActionType.THROW);
            ci.cancel();
        }
    }

    @Shadow
    protected abstract void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType);

    @Shadow
    public abstract void onClose();
}
