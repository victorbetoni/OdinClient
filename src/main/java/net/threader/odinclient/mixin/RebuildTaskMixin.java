package net.threader.odinclient.mixin;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.chunk.BlockBufferBuilderStorage;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.client.render.chunk.ChunkOcclusionDataBuilder;
import net.minecraft.client.render.chunk.ChunkRendererRegion;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.WorldChunk;
import net.threader.odinclient.feature.AbstractFeature;
import net.threader.odinclient.feature.hacks.XRayFeature;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

@Mixin(ChunkBuilder.BuiltChunk.RebuildTask.class)
public abstract class RebuildTaskMixin {

    @Shadow @Nullable protected ChunkRendererRegion region;

    @Shadow protected abstract <E extends BlockEntity> void addBlockEntity(ChunkBuilder.ChunkData data, Set<BlockEntity> blockEntities, E blockEntity);

    private ChunkBuilder.BuiltChunk.Task taskSuper =  (ChunkBuilder.BuiltChunk.Task) (Object) this;
    private ChunkBuilder.BuiltChunk builtSuper =  (ChunkBuilder.BuiltChunk) (Object) this;

    /**
     * @author
     */
    @Overwrite
    private Set<BlockEntity> render(float cameraX, float cameraY, float cameraZ, ChunkBuilder.ChunkData data, BlockBufferBuilderStorage buffers) {
        BlockPos blockPos = builtSuper.getOrigin().toImmutable();
        BlockPos blockPos2 = blockPos.add(15, 15, 15);
        ChunkOcclusionDataBuilder chunkOcclusionDataBuilder = new ChunkOcclusionDataBuilder();
        Set<BlockEntity> set = Sets.newHashSet();
        ChunkRendererRegion chunkRendererRegion = this.region;
        this.region = null;
        MatrixStack matrixStack = new MatrixStack();
        if (chunkRendererRegion != null) {
            BlockModelRenderer.enableBrightnessCache();
            Random random = new Random();
            BlockRenderManager blockRenderManager = MinecraftClient.getInstance().getBlockRenderManager();
            Iterator var15 = BlockPos.iterate(blockPos, blockPos2).iterator();

            while(var15.hasNext()) {
                BlockPos blockPos3 = (BlockPos)var15.next();
                BlockState blockState = chunkRendererRegion.getBlockState(blockPos3);
                Block block = blockState.getBlock();
                if (blockState.isOpaqueFullCube(chunkRendererRegion, blockPos3)) {
                    chunkOcclusionDataBuilder.markClosed(blockPos3);
                }

                if (block.hasBlockEntity()) {
                    BlockEntity blockEntity = chunkRendererRegion.getBlockEntity(blockPos3, WorldChunk.CreationType.CHECK);
                    if (blockEntity != null) {
                        this.addBlockEntity(data, set, blockEntity);
                    }
                }

                FluidState fluidState = chunkRendererRegion.getFluidState(blockPos3);
                RenderLayer renderLayer2;
                BufferBuilder bufferBuilder2;
                if (!fluidState.isEmpty()) {
                    renderLayer2 = RenderLayers.getFluidLayer(fluidState);
                    bufferBuilder2 = buffers.get(renderLayer2);
                    if (data.initializedLayers.add(renderLayer2)) {
                        builtSuper.beginBufferBuilding(bufferBuilder2);
                    }

                    if (blockRenderManager.renderFluid(blockPos3, chunkRendererRegion, bufferBuilder2, fluidState)) {
                        data.empty = false;
                        data.nonEmptyLayers.add(renderLayer2);
                    }
                }

                if (blockState.getRenderType() != BlockRenderType.INVISIBLE) {
                    renderLayer2 = RenderLayers.getBlockLayer(blockState);
                    bufferBuilder2 = buffers.get(renderLayer2);
                    if (data.initializedLayers.add(renderLayer2)) {
                        builtSuper.beginBufferBuilding(bufferBuilder2);
                    }

                    matrixStack.push();
                    matrixStack.translate((blockPos3.getX() & 15), (blockPos3.getY() & 15), (blockPos3.getZ() & 15));
                    if(AbstractFeature.instance(XRayFeature.class).isActivated()) {
                        if (blockRenderManager.renderBlock(blockState, blockPos3, chunkRendererRegion, matrixStack, bufferBuilder2, true, random)) {
                            data.empty = false;
                            data.nonEmptyLayers.add(renderLayer2);
                        }
                    }

                    matrixStack.pop();
                }
            }

            if (data.nonEmptyLayers.contains(RenderLayer.getTranslucent())) {
                BufferBuilder bufferBuilder3 = buffers.get(RenderLayer.getTranslucent());
                bufferBuilder3.sortQuads(cameraX - (float)blockPos.getX(), cameraY - (float)blockPos.getY(), cameraZ - (float)blockPos.getZ());
                data.bufferState = bufferBuilder3.popState();
            }

            data.initializedLayers.stream().map(buffers::get).forEach(buffer -> ((BufferBuilder) buffer).end());
            BlockModelRenderer.disableBrightnessCache();
        }

        data.occlusionGraph = chunkOcclusionDataBuilder.build();
        return set;
    }

}
