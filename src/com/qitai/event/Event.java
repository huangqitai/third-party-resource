package com.qitai.event;

import java.util.EventObject;

public class Event extends EventObject {
    private int EventState;

    public Event(Object source) {
        super(source);
    }

    @Override
    public Object getSource() {
        return super.getSource();
    }

    public int getEventState() {
        return EventState;
    }

    public void setEventState(int eventState) {
        EventState = eventState;
    }
}
