package net.threader.odinclient.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.Packet;
import net.threader.odinclient.OdinClient;
import net.threader.odinclient.event.network.PacketEvent;
import net.threader.signal.EventListener;
import net.threader.signal.Handler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClientPlayerInterface implements EventListener {
    private UUID uid;
    private MinecraftClient minecraft = MinecraftClient.getInstance();
    private ClientPlayNetworkHandler networkHandler = minecraft.getNetworkHandler();
    private Map<Class<? extends Packet>, Long> lastTimeReceived = new HashMap<>();
    private Map<Class<? extends Packet>, Long> lastTimeSent = new HashMap<>();

    public ClientPlayerInterface(UUID uid) {
        this.uid = uid;
    }

    public UUID getUUID() {
        return uid;
    }

    public void sendPacket(Packet<?> packet) {
        PacketEvent.C2S event = new PacketEvent.C2S(packet);
        OdinClient.INSTANCE.getEventProcessor().post(event);
        if(!event.isCancelled()) {
            networkHandler.sendPacket(packet);
            lastTimeSent.remove(packet.getClass());
            lastTimeSent.put(packet.getClass(), System.currentTimeMillis());
        }
    }

    @Handler(priority = -999)
    public void handlePacketReceived(PacketEvent.S2C event) {
        lastTimeReceived.remove(event.getPacket().getClass());
        lastTimeReceived.put(event.getPacket().getClass(), System.currentTimeMillis());
    }
}
