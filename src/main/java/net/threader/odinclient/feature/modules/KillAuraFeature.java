package net.threader.odinclient.feature.modules;

import net.threader.odinclient.feature.AbstractFeature;
import net.threader.odinclient.feature.ConfigurableAbstractFeature;
import net.threader.odinclient.feature.Feature;
import net.threader.odinclient.keybind.Keybindable;
import org.lwjgl.glfw.GLFW;

import java.io.File;

@Feature(id = "kill-aura", description = "Hit everyone and everything around you")
public class KillAuraFeature extends AbstractFeature implements Keybindable {
    public KillAuraFeature(String id, String description, boolean activated) {
        super(id, description, activated);
    }

    @Override
    public int getKey() {
        return GLFW.GLFW_KEY_K;
    }

    @Override
    public String onKey() {
        return "$odinclient toggle " + id;
    }
}
