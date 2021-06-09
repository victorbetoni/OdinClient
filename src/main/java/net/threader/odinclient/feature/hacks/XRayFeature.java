package net.threader.odinclient.feature.hacks;

import net.minecraft.util.registry.Registry;
import net.threader.odinclient.OdinClient;
import net.threader.odinclient.event.BlockTesselateEvent;
import net.threader.odinclient.feature.ConfigurableAbstractFeature;
import net.threader.odinclient.internal.api.event.EventListener;
import net.threader.odinclient.internal.api.event.Handler;
import net.threader.odinclient.feature.AbstractFeature;
import net.threader.odinclient.feature.Feature;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
@Feature(id = XRayFeature.ID)
public class XRayFeature extends ConfigurableAbstractFeature {

    public static final String ID = "xray";
    private final Set<String> visibleBlocks = new HashSet<>();
    private final Supplier<JSONObject> BLOCKS_JSON_FACTORY = () -> {
        JSONObject jsonObject = new JSONObject();
        JSONArray identifier = new JSONArray();
        identifier.addAll(visibleBlocks);
        jsonObject.put("visible", identifier);
        return jsonObject;
    };

    public XRayFeature(boolean activated) {
        super(ID, "See through the wall!", activated);
    }

    public Set<String> getVisibleBlocks() {
        return visibleBlocks;
    }

    @Override
    public void onLoad() {
        configurationFile = OdinClient.INSTANCE.createIfNotExist(new File(OdinClient.INSTANCE.getFeatureConfigFolder(), "xray_blocks.json"), false,
                this::read,
                (file) -> {
                    visibleBlocks.addAll(Registry.BLOCK.getEntries().stream()
                            .map(entry -> entry.getKey().getValue().toString())
                            .filter(identifier -> identifier.contains("ore"))
                            .collect(Collectors.toSet()));
                    this.save(file);
                });
        OdinClient.INSTANCE.getEventProcessor().register(new BlockRenderHandler());
        this.save();
    }

    @Override
    public void save(File config) {
        try (FileWriter writer = new FileWriter(config)) {
            writer.write(BLOCKS_JSON_FACTORY.get().toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void read(File config) {
        try (FileReader reader = new FileReader(config)) {
            JSONObject jsonResources = (JSONObject) new JSONParser().parse(reader);
            JSONArray visibleBlocks = (JSONArray) jsonResources.get("visible");
            visibleBlocks.forEach(block -> visibleBlocks.add((String) block));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static class BlockRenderHandler extends EventListener<BlockTesselateEvent> {

        public BlockRenderHandler() {
            super(BlockTesselateEvent.class);
        }

        @Handler
        public void handleRender(BlockTesselateEvent event) {
            if(AbstractFeature.instance(XRayFeature.class).isActivated()
                    && !AbstractFeature.instance(XRayFeature.class).getVisibleBlocks()
                    .contains(Registry.BLOCK.getId(event.getState().getBlock()).toString())) {
                event.setCanceled(true);
            }
        }

    }
}
