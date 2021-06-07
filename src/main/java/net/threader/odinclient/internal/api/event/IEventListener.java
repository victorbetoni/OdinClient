package net.threader.odinclient.internal.api.event;

public abstract class IEventListener<E extends IEvent> {
    private Class<E> listenedEvent;

    public IEventListener(Class<E> listenedEvent) {
        this.listenedEvent = listenedEvent;
    }

    public Class<E> getListenedEvent() {
        return listenedEvent;
    }
}
