package net.threader.odinclient;

import net.minecraft.client.MinecraftClient;
import net.threader.odinclient.api.event.OdinEventController;
import net.threader.odinclient.feature.FeatureManager;
import net.threader.odinclient.feature.hacks.XRayFeature;
import net.threader.odinclient.manager.KeybindManager;;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Consumer;

public enum OdinClient {
    INSTANCE;

    private File odinFolder;
    private File keybindsFile;
    private File minecraftFolder;

    private OdinEventController eventController = new OdinEventController();
    private KeybindManager keybindManager = new KeybindManager();
    private FeatureManager featureManager = new FeatureManager();

    @SuppressWarnings("all")
    public void initialize() {
        minecraftFolder = MinecraftClient.getInstance().runDirectory;
        odinFolder = createIfNotExist(new File(minecraftFolder.toPath().normalize().resolve("odinclient").toString()), true, null);
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
            if(!file.exists()) {
                Path dummy = dir ? Files.createDirectories(file.toPath()) : Files.createFile(file.toPath());
            }
            if(action != null) {
                action.accept(file);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return file;
    }
}
