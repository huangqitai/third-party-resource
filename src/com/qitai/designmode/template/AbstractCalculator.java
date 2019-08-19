package com.qitai.designmode.template;

public abstract class AbstractCalculator {
    public final int calculator(int[] a){
        System.out.println("抽象类具体方法");
        return calculator(a[0],a[1]);
    }

    abstract public int calculator(int num1 , int num2);
}
