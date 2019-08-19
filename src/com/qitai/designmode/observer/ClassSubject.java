package com.qitai.designmode.observer;

public class ClassSubject extends AbstractSubject {
    @Override
    public void operation() {
        notifyObservers();
    }
}
