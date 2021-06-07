package net.threader.odinclient.internal.api.command;

import net.threader.odinclient.internal.api.event.EventListener;
import net.threader.odinclient.internal.api.event.Handler;
import net.threader.odinclient.event.OdinCommandProcessEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class OdinCommandProcessor {

    public static final Map<String, CommandRunner> COMMAND_REGISTRY = new HashMap<>();

    public static class Distributor implements EventListener<OdinCommandProcessEvent> {
        @Handler
        public void distribute(OdinCommandProcessEvent event) {
            if(event.getCommand().split(" ").length > 1) {
                String[] args = event.getCommand().split(" ");
                String command = args[1];
                if(OdinCommandProcessor.COMMAND_REGISTRY.containsKey(command)) {
                    ArrayList<String> reorganizedArgs = (ArrayList<String>) Arrays.asList(args);
                    reorganizedArgs.remove(0);
                    reorganizedArgs.remove(1);
                    OdinCommandProcessor.COMMAND_REGISTRY.get(command).run((String[]) reorganizedArgs.toArray());
                }
            }
        }
    }

}
