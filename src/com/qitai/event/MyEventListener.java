package com.qitai.event;

import java.util.EventListener;

public class MyEventListener implements EventListener {

    public void doEvent(Event event){
        if (event.getEventState()==1){
            System.out.println("事件触发");
        }
    }

}
