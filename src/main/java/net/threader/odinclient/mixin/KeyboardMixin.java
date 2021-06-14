package net.threader.odinclient.mixin;

import net.minecraft.client.Keyboard;
import net.minecraft.client.network.ClientPlayerEntity;
import net.threader.odinclient.OdinClient;
import net.threader.odinclient.event.KeyPressedEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {
    @Inject(method = "onKey", at = @At("TAIL"))
    public void onKey(long window, int key, int scancode, int i, int j, CallbackInfo ci) {
        OdinClient.INSTANCE.getEventProcessor().post(new KeyPressedEvent(key));
    }
}
