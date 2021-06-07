package net.threader.odinclient.api.event;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import net.minecraft.client.MinecraftClient;
import net.threader.odinclient.util.OdinUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class OdinEventController {

    private static final ExecutorService executorService = Executors.newCachedThreadPool(
            new ThreadFactoryBuilder()
                    .setNameFormat("event-caller-%s")
                    .setDaemon(true)
                    .setPriority(Thread.MAX_PRIORITY)
                    .build());

    private Multimap<Class<? extends OdinEvent>, EventListener<? extends OdinEvent>> handlers = ArrayListMultimap.create();
    private Queue<OdinEvent> eventQueue = new ConcurrentLinkedQueue<>();

    public <E extends OdinEvent> void post(E event) {
        eventQueue.add(event);
    }

    public void register(EventListener<? extends OdinEvent> listener) {
        handlers.put((Class<OdinEvent>) listener.getClass().getTypeParameters()[0].getClass(), listener);
    }

    public void init() {
        executorService.submit(() -> {
            while(true) {
                if(!eventQueue.isEmpty()) {
                    OdinEvent event = eventQueue.poll();
                    Optional.of(handlers.get(event.getClass())).ifPresent(handlers -> handlers.forEach(handler ->
                            filterHandlerMethods((EventListener<OdinEvent>) handler, event.getClass()).forEach(method -> {
                                MinecraftClient.getInstance().execute(() -> {
                                    try {
                                        method.invoke(handler, event);
                                    } catch (IllegalAccessException | InvocationTargetException e) {
                                        e.printStackTrace();
                                    }
                                });
                            })));
                }
            }

        });
    }

    public <E extends OdinEvent> Collection<Method> filterHandlerMethods(EventListener<OdinEvent> listener, Class<E> event) {
        return Arrays.asList(listener.getClass().getDeclaredMethods()).stream().filter(method -> method.getParameterTypes().length == 1)
                .filter(method -> method.getParameterTypes()[0].equals(event)).collect(Collectors.toCollection(HashSet::new));
    }
}
