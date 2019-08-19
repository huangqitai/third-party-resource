package com.qitai.collection;

public class Queue {
    int[] arr;
    int len = 0;

    public Queue(){
        arr = new int[100];
    }

    public Queue(int n){
        arr = new int[n];
    }

    public int size(){
        return len;
    }

    public void print(){
        for (int i = 0 ; i < len ; i++){
            System.out.print(arr[i]+"   ");
        }
        System.out.println();
    }

    public void add(int a){
        if(len == arr.length) {
            System.out.println("队列已满，添加失败");
        }

        arr[len] = a;
        len++;
    }

    public Integer poll(){
        if(len == 0){
            return null;
        }
        return arr[0];
    }

    public void remove(){
        if(len == 0){
            System.out.println("队列为空");
        }
        for (int i = 0 ; i<len ;i++){
            arr[i] = arr[i+1];
        }
        len --;
    }
}
