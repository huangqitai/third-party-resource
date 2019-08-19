package com.qitai.designmode.factory;

public class FactoryPattern {
    public static Fruit getApple(){
        return new Apple();
    }

    public static Fruit getOrange(){
        return new Orange();
    }
}
