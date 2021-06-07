package net.threader.odinclient.api.event;

public interface Cancelable {
    void setCanceled(boolean bool);
    boolean isCanceled();
}
