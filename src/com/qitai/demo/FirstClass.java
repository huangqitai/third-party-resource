package com.qitai.demo;

public class FirstClass {

    public static void main(String[] args) {
        System.out.println("这是我的第一个Intellij IDEA JavaClass!");
        int temp1 = 100;
        int temp2 = 50;
        int temp3 = addNum(temp1 , temp2);
        System.out.println("这是temp1和temp2的和："+temp3);
    }

    public static Integer addNum(Integer temp1 , Integer temp2 ){
        int temp3 = temp1+temp2;
        return  temp3;
    }
}
