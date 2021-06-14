package net.threader.odinclient.event;

import net.minecraft.block.Block;
import net.threader.odinclient.internal.api.event.ICancelable;
import net.threader.odinclient.internal.api.event.IEvent;

public class BlockTranslucencyDefineEvent implements IEvent, ICancelable {

    private Block block;
    private boolean canceled;

    public BlockTranslucencyDefineEvent(Block block) {
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }

    @Override
    public void setCanceled(boolean bool) {
        this.canceled = bool;
    }

    @Override
    public boolean isCanceled() {
        return canceled;
    }
}
