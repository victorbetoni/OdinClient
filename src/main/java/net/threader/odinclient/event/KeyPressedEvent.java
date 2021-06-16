package net.threader.odinclient.event;

import net.threader.signal.Event;

public class KeyPressedEvent extends Event {
    private int key;

    public KeyPressedEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
