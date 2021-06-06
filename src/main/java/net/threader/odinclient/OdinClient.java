package net.threader.odinclient;

import java.io.File;
import java.io.IOException;

public enum OdinClient {
    INSTANCE;

    private File odinFolder;
    private File keybindFiles;

    public void initialize() {
        odinFolder = createIfNotExist(new File(System.getenv("APPDATA"), "wurst"), true);
        keybindFiles = createIfNotExist(new File(odinFolder, "keybinds.json"), false);
    }

    private File createIfNotExist(File file, boolean dir) {
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
