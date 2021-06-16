package net.threader.odinclient.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.Packet;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClientPlayerInterface {
    private UUID uid;
    private MinecraftClient minecraft = MinecraftClient.getInstance();
    private ClientPlayNetworkHandler networkHandler = minecraft.getNetworkHandler();
    private Map<Class<? extends Packet<?>>, Long> lastTimeReceived = new HashMap<>();
    private Map<Class<? extends Packet<?>>, Long> lastTimeSent = new HashMap<>();

    public ClientPlayerInterface(UUID uid) {
        this.uid = uid;
    }

    public UUID getUUID() {
        return uid;
    }

    public void sendPacket(Packet<?> packet) {
        networkHandler.sendPacket(packet);
    }
}
