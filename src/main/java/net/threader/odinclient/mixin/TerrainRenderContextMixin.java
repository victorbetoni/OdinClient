package net.threader.odinclient.mixin;

import net.fabricmc.fabric.impl.client.indigo.renderer.render.TerrainRenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.threader.odinclient.OdinClient;
import net.threader.odinclient.event.BlockTesselateEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TerrainRenderContext.class)
public class TerrainRenderContextMixin {
    @Inject(method = "tesselateBlock", at = @At("HEAD"), cancellable = true, remap = false)
    public void injectedTesselateBlock(BlockState blockState, BlockPos blockPos, BakedModel model, MatrixStack matrixStack, CallbackInfoReturnable<Boolean> cir) {
        BlockTesselateEvent event = new BlockTesselateEvent(blockState, blockPos, model, matrixStack);
        OdinClient.INSTANCE.getEventProcessor().post(event);
        if(event.isCancelled()) {
            cir.setReturnValue(false);
        }
    }

}
