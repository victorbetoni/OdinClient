package net.threader.odinclient.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import net.threader.odinclient.OdinClient;
import net.threader.odinclient.api.events.BlockRenderEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(BlockRenderManager.class)
public abstract class BlockRenderManagerMixin {

    @Inject(method = "renderBlock", at = @At("HEAD"), cancellable = true)
    private void render(BlockState state, BlockPos pos,
                        BlockRenderView world, MatrixStack matrix,
                        VertexConsumer vertexConsumer, boolean cull,
                        Random random, CallbackInfoReturnable<Boolean> cir) {

        BlockRenderEvent event = new BlockRenderEvent(state, pos, world, matrix, vertexConsumer, cull, random);
        OdinClient.INSTANCE.getEventController().post(event);
        if(event.isCanceled()) {
            cir.cancel();
        }
    }

}
