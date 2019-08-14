package com.qitai.demo;

import java.lang.reflect.Method;

public class Reflection {
    public static void main(String[] args) {

        if (initialize()){
            if(startMethod()){
                if(destroy()){
                    System.out.println("程序结束");
                }else {
                    throw  new RuntimeException("销毁失败");
                }
            }else {
                throw  new RuntimeException("启动失败");
            }
        }else {
            throw  new RuntimeException("初始化失败");
        }
    }

    public static boolean initialize(){
        try {
            Class<?> demoOne = Class.forName("com.qitai.demo.DemoOne");//根据类名获取类
            Method methodOne = demoOne.getDeclaredMethod("methodOne");//得到私有方法
            methodOne.setAccessible(true);//这一句不太懂，我觉得就是允许该方法加载过来，可以在下面调用
            Object objectOne = demoOne.newInstance();//通过构造方法创建对象
            System.out.print("设定为初始化阶段:");
            methodOne.invoke(objectOne);//调用方法
            return true;
        } catch (Exception e) {
            System.out.println("初始化阶段出现异常："+e.getMessage());
            return false;
        }
    }
    public static boolean startMethod(){
        try {
            Class<?> demoTwo = Class.forName("com.qitai.demo.DemoTwo");
            Method methodTwo = demoTwo.getDeclaredMethod("methodTwo");
            methodTwo.setAccessible(true);
            Object objectOne = demoTwo.newInstance();
            System.out.print("设定为启动阶段:");
            methodTwo.invoke(objectOne);
            return true;
        } catch (Exception e) {
            System.out.println("启动阶段出现异常："+e.getMessage());
            return false;
        }
    }
    public static boolean destroy(){
        try {
            Class<?> demoTh = Class.forName("com.qitai.demo.DemoTh");
            Method methodTh = demoTh.getDeclaredMethod("methodTh");
            methodTh.setAccessible(true);
            Object objectOne = demoTh.newInstance();
            System.out.print("设定为销毁阶段:");
            methodTh.invoke(objectOne);
            return true;
        } catch (Exception e) {
            System.out.println("销毁阶段出现异常："+e.getMessage());
            return false;
        }
    }
}
