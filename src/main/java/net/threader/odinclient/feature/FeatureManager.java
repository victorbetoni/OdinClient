package net.threader.odinclient.feature;

import net.threader.odinclient.OdinClient;
import net.threader.odinclient.keybind.KeybindManager;
import net.threader.odinclient.keybind.Keybindable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FeatureManager {
    private final Map<String, AbstractFeature> loadedFeatures = new HashMap<>();

    @SafeVarargs
    @SuppressWarnings("unchecked")
    public final <T extends AbstractFeature> void loadAll(File stateJson, Class<T>... features) {
        Map<String, Boolean> stateMap = new HashMap<>();
        try (FileReader reader = new FileReader(stateJson)) {
            JSONObject jsonResources = (JSONObject) new JSONParser().parse(reader);
            jsonResources.keySet().stream().map(String.class::cast).forEach(key -> {
                stateMap.put((String) key, (boolean) jsonResources.get(key));
            });
        } catch (IOException | ParseException ex) {
            ex.printStackTrace();
        }
        Arrays.asList(features).forEach(featureClass -> {
            try {
                Constructor<T> constructor = featureClass.getConstructor(String.class, String.class, boolean.class);
                Feature featureAnnotation = featureClass.getAnnotation(Feature.class);
                if(featureAnnotation != null) {
                    String id = featureAnnotation.id();
                    String desc = featureAnnotation.description();
                    boolean loaded = stateMap.get(id);
                    T feature = constructor.newInstance(id, desc, loaded);
                    loadedFeatures.put(featureAnnotation.id(), feature);
                    if(feature instanceof Keybindable) {
                        Keybindable bindable = (Keybindable) feature;
                        OdinClient.INSTANCE.getKeybindManager().register(bindable.getKey(), bindable.onKey());
                    }
                    feature.handleLoad();
                }
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public <T extends AbstractFeature> T getFeature(Class<T> clazz) {
        return (T) loadedFeatures.values().stream().filter(x -> x.getClass().equals(clazz)).findFirst().get();
    }

    public <T extends AbstractFeature> Optional<T> getFeature(String id) {
        return (Optional<T>) Optional.ofNullable(loadedFeatures.get(id));
    }
}
