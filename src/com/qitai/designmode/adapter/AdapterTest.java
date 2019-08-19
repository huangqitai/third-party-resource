package com.qitai.designmode.adapter;

import org.junit.Test;

public class AdapterTest {
    @Test
    public void test(){

        TargetInterface ti = new TargetImplSourEx();
        ti.method2();
        ti.method1();
        //TargetInterface 接口就具有了Source类的功能
    }
}
