package net.threader.odinclient.internal.api.event;

public interface IState {
    enum State {
        ACCEPTED, DENIED, IGNORED;
    }

    State state();
    void setState(State st);
}
