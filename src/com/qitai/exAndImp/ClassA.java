package com.qitai.exAndImp;

public class ClassA implements MyInterface {
    @Override
    public void sum(int a, int b) {
        System.out.println(a+b);
    }
    @Override
    public void add(int a){
        System.out.println(a+a);
        a();
    }
    public void a(){
        System.out.println("ClassA的方法a");
    }

}
