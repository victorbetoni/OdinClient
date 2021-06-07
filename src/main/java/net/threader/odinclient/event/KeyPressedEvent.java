package net.threader.odinclient.event;

import net.threader.odinclient.internal.api.event.OdinEvent;

public class KeyPressedEvent implements OdinEvent {
    private int key;

    public KeyPressedEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
