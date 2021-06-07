package net.threader.odinclient.event;

import net.threader.odinclient.internal.api.event.OdinEvent;

public class OdinCommandProcessEvent implements OdinEvent {
    private String command;

    public OdinCommandProcessEvent(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
