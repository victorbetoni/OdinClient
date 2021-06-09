package net.threader.odinclient.feature.hacks;

import net.minecraft.util.registry.Registry;
import net.threader.odinclient.OdinClient;
import net.threader.odinclient.event.BlockTesselateEvent;
import net.threader.odinclient.internal.api.event.IEventListener;
import net.threader.odinclient.internal.api.event.Handler;
import net.threader.odinclient.feature.AbstractFeature;
import net.threader.odinclient.feature.Feature;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
@Feature(id = XRayFeature.ID)
public class XRayFeature extends AbstractFeature {

    public static final String ID = "xray";
    private File configFile;
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
        configFile = OdinClient.INSTANCE.createIfNotExist(new File(OdinClient.INSTANCE.getFeatureConfigFolder(), "xray_blocks.json"), false,
                null,
                (file) -> visibleBlocks.addAll(Registry.BLOCK.getEntries().stream()
                        .map(entry -> entry.getKey().getValue().toString())
                        .filter(identifier -> identifier.contains("ore"))
                        .collect(Collectors.toSet())));
        OdinClient.INSTANCE.getEventProcessor().register(new BlockRenderHandler());
        this.reload();
    }

    @Override
    public void reload() {
        try (FileWriter writer = new FileWriter(configFile)) {
            writer.write(BLOCKS_JSON_FACTORY.get().toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class BlockRenderHandler extends IEventListener<BlockTesselateEvent> {

        public BlockRenderHandler() {
            super(BlockTesselateEvent.class);
        }

        @Handler
        public void handleRender(BlockTesselateEvent event) {
            if(AbstractFeature.instance(XRayFeature.class).isActivated()
                    && AbstractFeature.instance(XRayFeature.class).getVisibleBlocks()
                    .contains(Registry.BLOCK.getId(event.getState().getBlock()).toString())) {
                event.setCanceled(true);
            }
        }

    }
}
