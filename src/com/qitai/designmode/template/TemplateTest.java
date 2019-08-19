package com.qitai.designmode.template;

import org.junit.Test;

public class TemplateTest {
    @Test
    public void test(){
        int[] a = new int[2];
        a[0] = 3;
        a[1] = 5;
        AbstractCalculator ac = new Calculator();
        //执行完抽象类的具象化calculator方法之后，执行被继承类重写的抽象calculator方法
        int sum = ac.calculator(a);
        System.out.println(sum);
    }
}
