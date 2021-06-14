package net.threader.odinclient.event;

import net.minecraft.block.Block;
import net.threader.odinclient.internal.api.event.ICancelable;
import net.threader.odinclient.internal.api.event.IEvent;

public class BlockFacesRenderEvent implements IEvent, ICancelable {
    private Block block;
    private boolean canceled;

    public BlockFacesRenderEvent(Block block) {
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }

    @Override
    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    @Override
    public boolean isCanceled() {
        return canceled;
    }
}
