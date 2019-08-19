package com.qitai.designmode.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSubject implements Subject {
    private List<Observer> lo = new ArrayList<>();
    @Override
    public void addObserver(Observer observer) {
        lo.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        lo.remove(observer);
    }

    @Override
    public void notifyObservers() {
        lo.forEach(Observer::update);
    }
}
