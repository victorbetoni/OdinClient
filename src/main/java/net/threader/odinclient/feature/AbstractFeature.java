package net.threader.odinclient.feature;

import net.threader.odinclient.OdinClient;

public abstract class AbstractFeature {
    protected String id;
    protected boolean activated;
    protected String description;

    public AbstractFeature(String id, String description, boolean activated) {
        this.id = id;
        this.description = description;
        this.activated = activated;
    }

    public static <T extends AbstractFeature> T instance(Class<T> clazz) {
        return OdinClient.INSTANCE.getFeatureManager().getFeature(clazz);
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
        if(activated) {
            this.handleEnable();
        } else {
            this.handleDisable();
        }
    }

    public void reload() {}

    public void handleLoad() {}
    public void handleDisable() {}
    public void handleEnable() {}
}
