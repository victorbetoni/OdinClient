package net.threader.odinclient.feature;

import net.minecraft.client.render.block.BlockRenderManager;

@Feature(id = XRayFeature.ID)
public class XRayFeature extends AbstractFeature {

    public static final String ID = "xray";

    public XRayFeature(boolean activated) {
        super(ID, "See through the wall!", activated);
    }
}
