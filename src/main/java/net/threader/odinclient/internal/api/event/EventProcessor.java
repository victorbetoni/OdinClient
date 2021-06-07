package net.threader.odinclient.internal.api.event;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class EventProcessor {

    private Multimap<Class<? extends IEvent>, IEventListener<? extends IEvent>> handlers = ArrayListMultimap.create();

    public void register(IEventListener<? extends IEvent> listener) {
        handlers.put(listener.getListenedEvent(), listener);
    }

    public <E extends IEvent> void post(E event) {
        System.out.println("EVENT POSTED: " + event.getClass().getName());
        Optional.of(handlers.get(event.getClass())).ifPresent(handlers -> handlers.forEach(handler -> {
            System.out.println("HANDLER ENCONTRADO: " + handler.getClass().getName());
                    filterHandlerMethods((IEventListener<IEvent>) handler, event.getClass()).forEach(method -> {
                        System.out.println("METODO ENCONTRADO: " + method.getDeclaringClass().getName());
                        try {
                            method.invoke(handler, event);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    });
                }));
    }

    public <E extends IEvent> Collection<Method> filterHandlerMethods(IEventListener<IEvent> listener, Class<E> event) {
        return Arrays.asList(listener.getClass().getDeclaredMethods()).stream().filter(method -> method.getParameterTypes().length == 1)
                .filter(method -> method.getParameterTypes()[0].equals(event))
                .filter(method -> method.getAnnotation(Handler.class) != null)
                .collect(Collectors.toCollection(HashSet::new));
    }
}
