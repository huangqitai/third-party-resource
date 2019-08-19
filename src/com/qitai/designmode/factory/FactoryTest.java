package com.qitai.designmode.factory;

import org.junit.Test;

public class FactoryTest {
    @Test
    public void test(){
        Fruit fruit = FactoryPattern.getApple();
        fruit.kind();
    }
}
