package com.qitai.designmode.singleton;

import org.junit.Test;

public class CreateSingleton {
    @Test
    public void test(){
        SingletonTest s1 = SingletonTest.getInstance();
        SingletonTest s2 = SingletonTest.getInstance();
//        SingletonTest s3 = new SingletonTest();
//        SingletonTest s4 = new SingletonTest();
        //单例的对象hashcode值是一样的，如果构造方法没有私有化的时候，new的对象hashcode值不一样
        System.out.println(s1.hashCode());

        System.out.println(s2.hashCode());
//        System.out.println(s3.hashCode());
//        System.out.println(s4.hashCode());
    }
}
