package net.threader.odinclient;

import net.minecraft.client.MinecraftClient;
import net.threader.odinclient.client.ClientPlayerInterface;
import net.threader.odinclient.command.ClientCommands;
import net.threader.odinclient.internal.api.command.CommandProcessor;
import net.threader.odinclient.feature.FeatureManager;
import net.threader.odinclient.feature.modules.XRayModule;
import net.threader.odinclient.keybind.KeybindManager;
import net.threader.signal.EventProcessor;;

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
    private File featureConfigFolder;

    private CommandProcessor commandProcessor = new CommandProcessor();
    private EventProcessor eventProcessor = new EventProcessor();
    private KeybindManager keybindManager = new KeybindManager();
    private FeatureManager featureManager = new FeatureManager();

    private ClientPlayerInterface client = new ClientPlayerInterface(MinecraftClient.getInstance().player.getUuid());

    @SuppressWarnings("all")
    public void initialize() {
        minecraftFolder = MinecraftClient.getInstance().runDirectory;
        odinFolder = createIfNotExist(new File(minecraftFolder.toPath().normalize().resolve("odinclient").toString()), true, null, null);
        featureConfigFolder = createIfNotExist(new File(odinFolder, "configs"), true, null, null);
        featuresStateFile = createIfNotExist(new File(odinFolder, "features.json"), false,
                (file) -> featureManager.loadAll(file, XRayModule.class), null);
        keybindsFile = createIfNotExist(new File(odinFolder, "keybinds.json"), false,
                (file) -> keybindManager.loadKeybinds(file), null);

        eventProcessor.register(new CommandProcessor.Distributor());
        eventProcessor.register(keybindManager);
        eventProcessor.register(client);

        commandProcessor.COMMAND_REGISTRY.put("toggle", new ClientCommands.ToggleFeatureCommand());
    }

    public CommandProcessor getCommandProcessor() {
        return commandProcessor;
    }

    public FeatureManager getFeatureManager() {
        return featureManager;
    }

    public EventProcessor getEventProcessor() {
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

    public File getFeatureConfigFolder() {
        return featureConfigFolder;
    }

    public File createIfNotExist(File file, boolean dir, Consumer<File> action, Consumer<File> ifNotExist) {
        try {
            if(!file.exists()) {
                Path dummy = dir ? Files.createDirectories(file.toPath()) : Files.createFile(file.toPath());
                if(ifNotExist != null) {
                    ifNotExist.accept(file);
                }
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
