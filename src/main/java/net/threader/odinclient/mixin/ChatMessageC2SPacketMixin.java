package net.threader.odinclient.mixin;

import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.threader.odinclient.OdinClient;
import net.threader.odinclient.event.CommandProcessEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatMessageC2SPacket.class)
public class ChatMessageC2SPacketMixin {
    @Inject(method = "<init>(Ljava/lang/String;)V", at = @At("HEAD"), cancellable = true)
    public void onConstruct(String message, CallbackInfo ci) {
        if(message.startsWith(OdinClient.Metrics.getOdinCommandPrefix())) {
            CommandProcessEvent event = new CommandProcessEvent(message);
            OdinClient.INSTANCE.getEventProcessor().post(event);
            ci.cancel();
        }
    }
}
