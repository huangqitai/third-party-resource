package com.qitai.event;

public class EventSource {
    private MyEventListener myEventListener;
    public void setListener(MyEventListener myEventListener){
        this.myEventListener = myEventListener;
    }

    public void tirgger(Event event){
        event.setEventState(1);
        notifyEvent(event);
    }
    public void notifyEvent(Event event){
        myEventListener.doEvent(event);
    }
}
