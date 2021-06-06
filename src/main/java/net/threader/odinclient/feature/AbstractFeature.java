package net.threader.odinclient.feature;

public abstract class AbstractFeature {
    protected String id;
    protected boolean activated;
    protected String description;

    public AbstractFeature(String id, String description, boolean activated) {
        this.id = id;
        this.description = description;
        this.activated = activated;
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
}
