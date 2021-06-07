package net.threader.odinclient;

import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.threader.odinclient.command.OdinCommands;
import net.threader.odinclient.internal.api.command.OdinCommandProcessor;
import net.threader.odinclient.internal.api.event.OdinEventProcessor;
import net.threader.odinclient.feature.FeatureManager;
import net.threader.odinclient.feature.hacks.XRayFeature;
import net.threader.odinclient.manager.KeybindManager;;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

public enum OdinClient {
    INSTANCE;

    private File odinFolder;
    private File keybindsFile;
    private File minecraftFolder;
    private File featuresStateFile;

    private OdinCommandProcessor commandProcessor = new OdinCommandProcessor();
    private OdinEventProcessor eventProcessor = new OdinEventProcessor();
    private KeybindManager keybindManager = new KeybindManager();
    private FeatureManager featureManager = new FeatureManager();

    @SuppressWarnings("all")
    public void initialize() {
        minecraftFolder = MinecraftClient.getInstance().runDirectory;
        odinFolder = createIfNotExist(new File(minecraftFolder.toPath().normalize().resolve("odinclient").toString()), true, null);
        featuresStateFile = createIfNotExist(new File(odinFolder, "features.json"), false,
                (file) -> featureManager.loadAll(file, XRayFeature.class));
        keybindsFile = createIfNotExist(new File(odinFolder, "keybinds.json"), false,
                (file) -> keybindManager.loadKeybinds(file));

        commandProcessor.COMMAND_REGISTRY.put("toggle", new OdinCommands.ToggleFeatureCommand());
    }

    public OdinCommandProcessor getCommandProcessor() {
        return commandProcessor;
    }

    public FeatureManager getFeatureManager() {
        return featureManager;
    }

    public OdinEventProcessor getEventProcessor() {
        return eventProcessor;
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

    public static class Metrics {
        private static final String odinCommandPrefix = "$odin";

        public static String getOdinCommandPrefix() {
            return odinCommandPrefix;
        }
    }
}
