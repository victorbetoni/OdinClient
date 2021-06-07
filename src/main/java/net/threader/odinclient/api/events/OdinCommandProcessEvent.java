package net.threader.odinclient.api.events;

import net.threader.odinclient.api.OdinEvent;

public class OdinCommandProcessEvent implements OdinEvent {
    private String command;

    public OdinCommandProcessEvent(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
