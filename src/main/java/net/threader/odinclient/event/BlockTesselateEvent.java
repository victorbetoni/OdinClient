package net.threader.odinclient.event;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.threader.odinclient.internal.api.event.ICancelable;
import net.threader.odinclient.internal.api.event.IEvent;

public class BlockTesselateEvent implements IEvent, ICancelable {
    private boolean canceled;
    private BlockState blockState;
    private BlockPos blockPos;
    private BakedModel model;
    private Block block;
    private MatrixStack matrixStack;

    public BlockTesselateEvent(BlockState state, BlockPos pos, BakedModel model, MatrixStack matrix) {
        this.blockState = state;
        this.blockPos = pos;
        this.model = model;
        this.matrixStack = matrix;
        this.block = state.getBlock();
    }

    public Block getBlock() {
        return block;
    }

    public BlockState getState() {
        return blockState;
    }

    public BlockPos getPos() {
        return blockPos;
    }

    public BakedModel getModel() {
        return model;
    }

    public MatrixStack getMatrix() {
        return matrixStack;
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
