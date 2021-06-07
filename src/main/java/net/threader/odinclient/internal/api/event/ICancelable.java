package net.threader.odinclient.internal.api.event;

public interface ICancelable {
    void setCanceled(boolean bool);
    boolean isCanceled();
}
