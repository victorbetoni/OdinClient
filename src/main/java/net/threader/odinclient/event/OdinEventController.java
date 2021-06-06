package net.threader.odinclient.event;

import net.minecraft.client.render.chunk.ChunkBuilder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public enum  OdinEventController {
    INSTANCE;

    private Map<OdinEvent, Collection<Class<? extends OdinEvent>>> handlers = new HashMap<>();


}
