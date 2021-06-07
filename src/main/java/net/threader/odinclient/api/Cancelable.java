package net.threader.odinclient.api;

public interface Cancelable {
    void setCanceled(boolean bool);
    boolean isCanceled();
}
