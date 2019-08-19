package com.qitai.designmode.observer;

public class ObserverOne implements Observer {
    @Override
    public void update() {
        System.out.println("OberverOne 收到通知");
    }
}
