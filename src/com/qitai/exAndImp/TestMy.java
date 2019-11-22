package com.qitai.exAndImp;

import org.junit.Test;

public class TestMy {
    @Test
    public void myTest(){
        MyInterface myInterface = new ClassB();
        myInterface.sum(4,5);
        myInterface.add(4);
    }
}
