package net.threader.odinclient.feature.hacks;

import net.minecraft.util.Identifier;
import net.threader.odinclient.feature.AbstractFeature;
import net.threader.odinclient.feature.Feature;

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
}
