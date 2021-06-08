package net.threader.odinclient.event;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import net.threader.odinclient.internal.api.event.ICancelable;
import net.threader.odinclient.internal.api.event.IEvent;

import java.util.Random;

public class BlockModelRenderEvent implements IEvent, ICancelable {
    private BlockRenderView world;
    private BakedModel model;
    private BlockState state;
    private BlockPos pos;
    private MatrixStack buffer;
    private VertexConsumer vertexConsumer;
    private boolean cull;
    private Random random;
    private long seed;
    private int overlay;
    private boolean canceled;

    public BlockModelRenderEvent(BlockRenderView world, BakedModel model, BlockState state, BlockPos pos, MatrixStack buffer, VertexConsumer vertexConsumer, boolean cull, Random random, long seed, int overlay) {
        this.world = world;
        this.model = model;
        this.state = state;
        this.pos = pos;
        this.buffer = buffer;
        this.vertexConsumer = vertexConsumer;
        this.cull = cull;
        this.random = random;
        this.seed = seed;
        this.overlay = overlay;
    }

    public BlockRenderView getWorld() {
        return world;
    }

    public BakedModel getModel() {
        return model;
    }

    public BlockState getState() {
        return state;
    }

    public BlockPos getPos() {
        return pos;
    }

    public MatrixStack getBuffer() {
        return buffer;
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

    public long getSeed() {
        return seed;
    }

    public int getOverlay() {
        return overlay;
    }

    @Override
    public void setCanceled(boolean bool) {
        canceled = bool;
    }

    @Override
    public boolean isCanceled() {
        return canceled;
    }
}
