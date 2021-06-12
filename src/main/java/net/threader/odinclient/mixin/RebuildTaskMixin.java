package net.threader.odinclient.mixin;

import net.fabricmc.fabric.mixin.client.indigo.renderer.MixinChunkRebuildTask;
import net.minecraft.block.Block;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.client.render.chunk.ChunkOcclusionDataBuilder;
import net.minecraft.client.render.chunk.ChunkRendererRegion;
import net.minecraft.util.math.BlockPos;
import net.threader.odinclient.OdinClient;
import net.threader.odinclient.event.BlockTraluscenscyDefineEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Objects;

@Mixin(ChunkBuilder.BuiltChunk.RebuildTask.class)
public class RebuildTaskMixin {
    @Shadow @Nullable protected ChunkRendererRegion region;

    @Redirect(method = "render",
            at = @At(value = "HEAD", target = "Lnet/minecraft/client/render/chunk;markClosed(Lnet/minecraft/util/math/BlockPos;)V"))
    public void render(BlockPos pos) {
        ChunkRendererRegion region = this.region;
        if(region != null) {
            Block block = Objects.requireNonNull(region.getBlockEntity(pos)).getCachedState().getBlock();
            BlockTraluscenscyDefineEvent event = new BlockTraluscenscyDefineEvent(block);
            OdinClient.INSTANCE.getEventProcessor().post(event);
            if(!event.isCanceled()) {
                ChunkOcclusionDataBuilder builder = (ChunkOcclusionDataBuilder) (Object) this;
                builder.closed.set(ChunkOcclusionDataBuilder.pack(pos), true);
                --builder.openCount;
            }
        }
    }
}
