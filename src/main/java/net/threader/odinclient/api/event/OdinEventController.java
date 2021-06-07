package net.threader.odinclient.api.event;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

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

public class OdinEventController<T extends OdinEvent> {

    private static final ExecutorService executorService = Executors.newCachedThreadPool(
            new ThreadFactoryBuilder()
                    .setNameFormat("event-caller-%s")
                    .setDaemon(true)
                    .setPriority(Thread.MAX_PRIORITY)
                    .build());

    private Multimap<Class<T>, EventListener<T>> handlers = ArrayListMultimap.create();
    private Queue<T> eventQueue = new ConcurrentLinkedQueue<>();

    public void post(T event) {
        eventQueue.add(event);
    }

    public void register(Class<T> event, EventListener<T> listener) {
        handlers.put(event, listener);
    }

    public void init() {
        executorService.submit(() -> {
            while(true) {
                T event = eventQueue.poll();
                if(!eventQueue.isEmpty()) {
                    Optional.of(handlers.get((Class<T>) event.getClass())).ifPresent(handlers -> handlers.forEach(handler ->
                            filterHandlerMethods(handler, (Class<T>) event.getClass()).forEach(method -> {
                                try {
                                    method.invoke(handler, event);
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            })));
                }
            }

        });
    }

    public Collection<Method> filterHandlerMethods(EventListener<T> listener, Class<T> event) {
        return Arrays.asList(listener.getClass().getDeclaredMethods()).stream().filter(method -> method.getParameterTypes().length == 1)
                .filter(method -> method.getParameterTypes()[0].equals(event)).collect(Collectors.toCollection(HashSet::new));
    }
}
