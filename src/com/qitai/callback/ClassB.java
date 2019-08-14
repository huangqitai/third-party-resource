package com.qitai.callback;

import org.junit.Test;

public class ClassB implements CallbackInterface {
    @Override
    public void callback() {
        System.out.print("这是回调接口方法的重写");
    }

    @Test
    public void TestCall(){
        ClassA A = new ClassA(this);//给被调的类A传递自己，让A知道回调给谁
        A.backB();//指使A调用B的callback函数，对于B来说，callback就是一个回调方法

    }
}
