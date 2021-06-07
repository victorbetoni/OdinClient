package net.threader.odinclient.internal.api.event;

public interface Cancelable {
    void setCanceled(boolean bool);
    boolean isCanceled();
}
