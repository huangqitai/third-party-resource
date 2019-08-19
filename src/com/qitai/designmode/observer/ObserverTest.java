package com.qitai.designmode.observer;

import org.junit.Test;

public class ObserverTest {

    @Test
    public void test(){
        Subject subject = new ClassSubject();
        subject.addObserver(new ObserverOne());
        subject.addObserver(new ObserverTwo());

        subject.operation();
    }
}
