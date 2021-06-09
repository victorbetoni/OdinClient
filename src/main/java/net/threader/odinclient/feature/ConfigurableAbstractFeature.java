package net.threader.odinclient.feature;

import java.io.File;

public abstract class ConfigurableAbstractFeature extends AbstractFeature {
    protected File configurationFile;

    public ConfigurableAbstractFeature(String id, String description, boolean activated) {
        super(id, description, activated);
    }

    public void save() {
        save(configurationFile);
    }

    public void read() {
        read(configurationFile);
    }

    public abstract void save(File config);
    public abstract void read(File config);
}
