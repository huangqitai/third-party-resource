package com.qitai.lambda;

import org.junit.Test;

import java.util.LinkedList;

public class LambdaTest {

    @Test
    public void runLambda(){
        Thread thread = new Thread(()->System.out.print("run运行"));
        thread.start();
    }

    @Test
    public void runThread(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.print("run运行");
            }
        });
        thread.start();
    }

    @Test
    public void add(){
        Compute compute = new Compute() {
            @Override
            public void add(int x, int y) {
                System.out.print(x+y);
            }
        };
        compute.add(3,5);

        ComputeClass computeClass = new ComputeClass(){
            @Override
            public void add(int x, int y) {
                System.out.print(x+y);
            }
        };
        computeClass.add(2,3);
    }

    @Test
    public void addLambda(){
        Compute compute = (x,y) -> System.out.print(x+y);
        compute.add(3,4);

//        ComputeClass computeClass = new ComputeClass({
////            (int x,int y) -> System.out.print(x+y);
////        });
////        computeClass.add(4,4);
    }
}
