package net.threader.odinclient.event;

import net.minecraft.block.Block;
import net.threader.odinclient.internal.api.event.ICancelable;
import net.threader.odinclient.internal.api.event.IEvent;
import net.threader.odinclient.internal.api.event.IState;

public class BlockFacesForceRender implements IEvent, IState {
    private Block block;
    private State state = State.IGNORED;

    public BlockFacesForceRender(Block block) {
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }

    @Override
    public State state() {
        return state;
    }

    @Override
    public void setState(State st) {
        state = st;
    }
}
