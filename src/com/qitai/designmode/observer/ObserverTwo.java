package com.qitai.designmode.observer;

public class ObserverTwo implements Observer {
    @Override
    public void update() {
        System.out.println("ObserverTwo 收到通知");
    }
}
