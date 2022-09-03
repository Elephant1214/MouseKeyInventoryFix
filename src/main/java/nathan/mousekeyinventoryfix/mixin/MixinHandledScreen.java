package nathan.mousekeyinventoryfix.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.gui.screen.Screen.hasControlDown;

@Mixin(HandledScreen.class)
public abstract class MixinHandledScreen {
    protected MinecraftClient client;
    @Shadow
    @Nullable
    protected Slot focusedSlot;

    /**
     * @author Elephant_1214
     * @reason Fixes not being able to use mouse key binds to close inventories
     */
    @Inject(method = "onMouseClick(I)V", at = @At(value = "HEAD"))
    public void mouseCloseInventoryMixin(int button, CallbackInfo ci) {
        if (this.client.options.inventoryKey.matchesMouse(button)) {
            this.close();
            return;
        }
    }

    /**
     * @author Elephant_1214
     * @reason Fixes not being able to use mouse key binds to drop items
     */
    @Inject(method = "onMouseClick(I)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/option/GameOptions;swapHandsKey:Lnet/minecraft/client/option/KeyBinding;", opcode = Opcodes.GETFIELD))
    public void mouseDropMixin(int button, CallbackInfo ci) {
        if (this.client.options.dropKey.matchesMouse(button)) {
            this.onMouseClick(this.focusedSlot, this.focusedSlot.id, hasControlDown() ? 1 : 0, SlotActionType.THROW);
            return;
        }
    }

    @Shadow
    protected abstract void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType);

    @Shadow
    public abstract void close();
}
