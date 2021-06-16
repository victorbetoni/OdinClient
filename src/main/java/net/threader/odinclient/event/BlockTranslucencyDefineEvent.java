package net.threader.odinclient.event;

import net.minecraft.block.Block;
import net.threader.signal.Event;
import net.threader.signal.ICancellable;

public class BlockTranslucencyDefineEvent extends Event implements ICancellable {

    private Block block;
    private boolean canceled;

    public BlockTranslucencyDefineEvent(Block block) {
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }

    @Override
    public boolean isCancelled() {
        return canceled;
    }

    @Override
    public void setCancelled(boolean b) {
        canceled = b;
    }
}
