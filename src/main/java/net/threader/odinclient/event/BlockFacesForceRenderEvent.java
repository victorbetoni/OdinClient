package net.threader.odinclient.event;

import net.minecraft.block.Block;
import net.threader.signal.Event;

public class BlockFacesForceRenderEvent extends Event {
    public enum State {
        ACCEPTED, DENIED, IGNORED;
    }

    private Block block;
    private State state = State.IGNORED;

    public BlockFacesForceRenderEvent(Block block) {
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }

    public State state() {
        return state;
    }

    public void setState(State st) {
        state = st;
    }
}
