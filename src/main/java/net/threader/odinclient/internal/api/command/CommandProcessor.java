package net.threader.odinclient.internal.api.command;

import net.threader.odinclient.event.CommandProcessEvent;
import net.threader.signal.EventListener;
import net.threader.signal.Handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import java.util.Map;

public class CommandProcessor {

    public static final Map<String, CommandRunner> COMMAND_REGISTRY = new HashMap<>();

    public static class Distributor implements EventListener {

        @Handler
        public void distribute(CommandProcessEvent event) {
            System.out.println();
            if(event.getCommand().split(" ").length > 1) {
                String[] args = event.getCommand().split(" ");
                String command = args[1];
                if(CommandProcessor.COMMAND_REGISTRY.containsKey(command)) {
                    ArrayList<String> reorganizedArgs = new ArrayList<>(Arrays.asList(args));
                    reorganizedArgs.remove(0);
                    reorganizedArgs.remove(0);
                    Object[] array = reorganizedArgs.toArray();
                    CommandProcessor.COMMAND_REGISTRY.get(command).run(Arrays.copyOf(array, array.length, String[].class));
                }
            }
        }
    }

}
