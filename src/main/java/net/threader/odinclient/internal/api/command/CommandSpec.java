package net.threader.odinclient.internal.api.command;

public class CommandSpec {
    private String alias;
    private CommandRunner runner;

    public CommandSpec(String alias, CommandRunner runner) {
        this.alias = alias;
        this.runner = runner;
    }

    public String getAlias() {
        return alias;
    }

    public CommandRunner getRunner() {
        return runner;
    }
}
