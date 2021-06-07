package net.threader.odinclient.event;

import net.threader.odinclient.internal.api.event.IEvent;

public class KeyPressedEvent implements IEvent {
    private int key;

    public KeyPressedEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
