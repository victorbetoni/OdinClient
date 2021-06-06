package net.threader.odinclient;

import net.minecraft.client.Keyboard;
import net.minecraft.client.input.KeyboardInput;
import net.threader.odinclient.manager.KeybindManager;
import net.threader.odinclient.util.OdinUtils;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public enum OdinClient {
    INSTANCE;

    private File odinFolder;
    private File keybindsFile;

    private KeybindManager keybindManager = new KeybindManager();

    public void initialize() {
        odinFolder = createIfNotExist(new File(System.getenv("APPDATA"), "wurst"), true, OdinUtils.dummyConsumer(File.class));
        keybindsFile = createIfNotExist(new File(odinFolder, "keybinds.json"), false, (file) -> {

        });
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
