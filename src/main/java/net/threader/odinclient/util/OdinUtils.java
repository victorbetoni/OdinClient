package net.threader.odinclient.util;

import java.util.function.Consumer;

public class OdinUtils {
    public static <T> Consumer<T> dummyConsumer(Class<T> type) {
        return (t) -> {};
    }
}
