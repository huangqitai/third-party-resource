package com.qitai.exAndImp;

public class ClassB extends ClassA {
    @Override
    public void sum(int a, int b) {
        System.out.println(a*b);
    }

    @Override
    public void a() {
        System.out.println("ClassB的方法a重写");
    }
}
