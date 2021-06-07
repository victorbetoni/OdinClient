package net.threader.odinclient.listener;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import net.threader.odinclient.api.event.Cancelable;
import net.threader.odinclient.api.event.OdinEvent;

import java.util.Random;

public class BlockRenderEvent implements OdinEvent, Cancelable {
    private boolean canceled;
    private BlockState state;
    private BlockPos pos;
    private BlockRenderView world;
    private MatrixStack matrix;
    private VertexConsumer vertexConsumer;
    private boolean cull;
    private Random random;

    public BlockRenderEvent(BlockState state, BlockPos pos, BlockRenderView world, MatrixStack matrix, VertexConsumer vertexConsumer, boolean cull, Random random) {
        this.state = state;
        this.pos = pos;
        this.world = world;
        this.matrix = matrix;
        this.vertexConsumer = vertexConsumer;
        this.cull = cull;
        this.random = random;
    }

    public BlockState getState() {
        return state;
    }

    public BlockPos getPos() {
        return pos;
    }

    public BlockRenderView getWorld() {
        return world;
    }

    public MatrixStack getMatrix() {
        return matrix;
    }

    public VertexConsumer getVertexConsumer() {
        return vertexConsumer;
    }

    public boolean isCull() {
        return cull;
    }

    public Random getRandom() {
        return random;
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
