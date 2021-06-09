package net.threader.odinclient.internal.api.event;

public abstract class EventListener<E extends IEvent> {
    private Class<E> listenedEvent;

    public EventListener(Class<E> listenedEvent) {
        this.listenedEvent = listenedEvent;
    }

    public Class<E> getListenedEvent() {
        return listenedEvent;
    }
}
