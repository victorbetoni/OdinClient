package net.threader.odinclient.feature.hacks;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.threader.odinclient.OdinClient;
import net.threader.odinclient.api.event.EventListener;
import net.threader.odinclient.api.event.OdinEvent;
import net.threader.odinclient.feature.AbstractFeature;
import net.threader.odinclient.feature.Feature;
import net.threader.odinclient.listener.BlockRenderEvent;

import java.util.HashSet;
import java.util.Set;

@Feature(id = XRayFeature.ID)
public class XRayFeature extends AbstractFeature {

    public static final String ID = "xray";
    private final Set<Identifier> visibleBlocks = new HashSet<>();

    public XRayFeature(boolean activated) {
        super(ID, "See through the wall!", activated);
    }

    public Set<Identifier> getVisibleBlocks() {
        return visibleBlocks;
    }

    @Override
    public void onLoad() {
        OdinClient.INSTANCE.getEventController().register(new BlockRenderHandler());
    }

    public static class BlockRenderHandler implements EventListener<BlockRenderEvent> {
        public void handleRender(BlockRenderEvent event) {
            if(AbstractFeature.instance(XRayFeature.class).isActivated()
                && AbstractFeature.instance(XRayFeature.class).getVisibleBlocks()
                    .contains(Registry.BLOCK.getId(event.getState().getBlock()))) {
                event.setCanceled(true);
            }
        }
    }
}
