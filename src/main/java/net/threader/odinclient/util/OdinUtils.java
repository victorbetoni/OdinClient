package net.threader.odinclient.util;

import java.util.function.Consumer;

public class OdinUtils {
    public static final <T> Consumer<T> dummyConsumer(Class<T> type) {
        return (t) -> {};
    }

    public static final Thread MINECRAFT_MAIN_THREAD = Thread.getAllStackTraces().keySet().stream()
            .filter(thread -> thread.getName().equalsIgnoreCase("main")).findFirst().get();
}
