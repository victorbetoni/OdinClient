package net.threader.odinclient.event.network;

import net.minecraft.network.Packet;
import net.threader.odinclient.internal.api.event.ICancelable;
import net.threader.odinclient.internal.api.event.IEvent;

public class PacketEvent {

    public static class C2S implements IEvent, ICancelable {

        private final Packet<?> packet;
        private boolean cancelled;

        public C2S(Packet<?> packet) {
            this.packet = packet;
        }

        public Packet<?> getPacket() {
            return packet;
        }

        @Override
        public void setCanceled(boolean bool) {
            cancelled = bool;
        }

        @Override
        public boolean isCanceled() {
            return cancelled;
        }
    }

    public static class S2C implements IEvent, ICancelable {

        private final Packet<?> packet;
        private boolean cancelled;

        public S2C(Packet<?> packet) {
            this.packet = packet;
        }

        public Packet<?> getPacket() {
            return packet;
        }

        @Override
        public void setCanceled(boolean bool) {
            cancelled = bool;
        }

        @Override
        public boolean isCanceled() {
            return cancelled;
        }
    }
}
