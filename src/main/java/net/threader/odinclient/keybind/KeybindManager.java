package net.threader.odinclient.keybind;

import net.minecraft.client.MinecraftClient;
import net.threader.odinclient.event.KeyPressedEvent;
import net.threader.odinclient.internal.api.event.EventListener;
import net.threader.odinclient.internal.api.event.Handler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class KeybindManager extends EventListener<KeyPressedEvent> {

    private Map<Integer, String> keybindMap = new HashMap<>();

    public KeybindManager() {
        super(KeyPressedEvent.class);
    }

    public void loadKeybinds(File file) {
        try (FileReader reader = new FileReader(file)) {
            keybindMap.clear();
            JSONObject jsonResources = (JSONObject) new JSONParser().parse(reader);
            jsonResources.keySet().forEach(obj -> {
                String key = (String) (jsonResources).get(obj);
                String command = (String) jsonResources.get(key);
                keybindMap.put(Integer.parseInt(key), command);
            });

        } catch (IOException | ParseException ex) {
            ex.printStackTrace();
        }
    }

    @Handler
    public void onKey(KeyPressedEvent event) {
        getCommand(event.getKey()).ifPresent(x -> {
            Objects.requireNonNull(MinecraftClient.getInstance().player, "Player cannot be null");
            MinecraftClient.getInstance().player.sendChatMessage(x);
        });
    }

    public void register(int key, String command) {
        keybindMap.put(key, command);
    }

    private Optional<String> getCommand(int key) {
        return Optional.ofNullable(keybindMap.get(key));
    }
}
