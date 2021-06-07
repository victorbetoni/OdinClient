package net.threader.odinclient;

import net.threader.odinclient.api.event.OdinEventController;
import net.threader.odinclient.feature.FeatureManager;
import net.threader.odinclient.feature.hacks.XRayFeature;
import net.threader.odinclient.manager.KeybindManager;
import net.threader.odinclient.util.OdinUtils;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public enum OdinClient {
    INSTANCE;

    private File odinFolder;
    private File keybindsFile;

    private OdinEventController eventController = new OdinEventController();
    private KeybindManager keybindManager = new KeybindManager();
    private FeatureManager featureManager = new FeatureManager();

    @SuppressWarnings("all")
    public void initialize() {
        odinFolder = createIfNotExist(new File(System.getenv("APPDATA") + "\\.minecraft\\odinclient"), true, OdinUtils.dummyConsumer(File.class));
        keybindsFile = createIfNotExist(new File(odinFolder, "keybinds.json"), false,
                (file) -> keybindManager.loadKeybinds(file));

        featureManager.loadAll(XRayFeature.class);
    }

    public FeatureManager getFeatureManager() {
        return featureManager;
    }

    public OdinEventController getEventController() {
        return eventController;
    }

    public File getOdinFolder() {
        return odinFolder;
    }

    public File getKeybindsFile() {
        return keybindsFile;
    }

    public KeybindManager getKeybindManager() {
        return keybindManager;
    }

    private File createIfNotExist(File file, boolean dir, Consumer<File> action) {
        try {
            if(file.exists()) {
                if(dir) {
                    file.mkdirs();
                    return file;
                }
                file.createNewFile();
                return file;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return file;
    }
}
