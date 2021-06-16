package net.threader.odinclient.event;

import net.threader.signal.Event;

public class CommandProcessEvent extends Event {
    private String command;

    public CommandProcessEvent(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
