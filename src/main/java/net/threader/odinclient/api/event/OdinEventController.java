package net.threader.odinclient.api.event;

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
public class OdinEventController {

    private Multimap<Class<? extends OdinEvent>, EventListener<? extends OdinEvent>> handlers = ArrayListMultimap.create();

    public void register(EventListener<? extends OdinEvent> listener) {
        handlers.put((Class<OdinEvent>) listener.getClass().getTypeParameters()[0].getClass(), listener);
    }

    public <E extends OdinEvent> void post(E event) {
        Optional.of(handlers.get(event.getClass())).ifPresent(handlers -> handlers.forEach(handler ->
                filterHandlerMethods((EventListener<OdinEvent>) handler, event.getClass()).forEach(method -> {
                    try {
                        method.invoke(handler, event);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                })));
    }

    public <E extends OdinEvent> Collection<Method> filterHandlerMethods(EventListener<OdinEvent> listener, Class<E> event) {
        return Arrays.asList(listener.getClass().getDeclaredMethods()).stream().filter(method -> method.getParameterTypes().length == 1)
                .filter(method -> method.getParameterTypes()[0].equals(event)).collect(Collectors.toCollection(HashSet::new));
    }
}
