package net.threader.odinclient.feature;

import net.threader.odinclient.feature.hacks.XRayFeature;
import org.json.simple.JSONArray;
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
                Constructor<T> constructor = featureClass.getConstructor(boolean.class);
                String id = (String) featureClass.getField("ID").get(null);
                T feature = constructor.newInstance(stateMap.get(id));
                System.out.println("Loading feature: " + id + " state: " + stateMap.get(id));
                loadedFeatures.put(id, feature);
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchFieldException e) {
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
