package com.qitai.event;

import org.junit.Test;

public class EventTest {
    @Test
    public void eventTest(){
        EventSource eventSource = new EventSource();
        eventSource.setListener(new MyEventListener());
        eventSource.tirgger(new Event(eventSource));
    }
}
