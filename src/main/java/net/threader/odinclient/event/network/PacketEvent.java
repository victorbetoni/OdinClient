package net.threader.odinclient.event.network;

import net.minecraft.network.Packet;
import net.threader.signal.Event;
import net.threader.signal.ICancellable;

public class PacketEvent {

    public static class C2S extends Event implements ICancellable {

        private final Packet<?> packet;
        private boolean cancelled;

        public C2S(Packet<?> packet) {
            this.packet = packet;
        }

        public Packet<?> getPacket() {
            return packet;
        }

        @Override
        public boolean isCancelled() {
            return cancelled;
        }

        @Override
        public void setCancelled(boolean b) {
            cancelled = b;
        }
    }

    public static class S2C extends Event implements ICancellable {

        private final Packet<?> packet;
        private boolean cancelled;

        public S2C(Packet<?> packet) {
            this.packet = packet;
        }

        public Packet<?> getPacket() {
            return packet;
        }

        public void setCancelled(boolean bool) {
            cancelled = bool;
        }

        @Override
        public boolean isCancelled() {
            return cancelled;
        }
    }
}
