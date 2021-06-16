package net.threader.odinclient.mixin;

import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.threader.odinclient.OdinClient;
import net.threader.odinclient.event.network.PacketEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {
    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    public void injectOnRead(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo ci) {
        PacketEvent.S2C event = new PacketEvent.S2C(packet);
        OdinClient.INSTANCE.getEventProcessor().post(event);
        if(event.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(method = "send(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    public void injectOnSend(Packet<?> packet, CallbackInfo ci) {
        PacketEvent.C2S event = new PacketEvent.C2S(packet);
        OdinClient.INSTANCE.getEventProcessor().post(event);
        if(event.isCancelled()) {
            ci.cancel();
        }
    }
}
