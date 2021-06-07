package net.threader.odinclient.event;

import net.threader.odinclient.internal.api.event.IEvent;

public class CommandProcessEvent implements IEvent {
    private String command;

    public CommandProcessEvent(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
