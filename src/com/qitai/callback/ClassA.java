package com.qitai.callback;

import org.junit.Test;

public class ClassA {

    public CallbackInterface c;

    public ClassA(CallbackInterface c){
        this.c = c;
    }

    public void backB(){
        c.callback();
    }
}
