package net.threader.odinclient.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.threader.odinclient.OdinClient;
import net.threader.odinclient.event.CommandProcessEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    public void sendChatMessage(String message, CallbackInfo ci) {
        if(message.startsWith(OdinClient.Metrics.getOdinCommandPrefix())) {
            CommandProcessEvent event = new CommandProcessEvent(message);
            OdinClient.INSTANCE.getEventProcessor().post(event);
            ci.cancel();
        }
    }

}
