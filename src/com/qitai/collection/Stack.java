package com.qitai.collection;

public class Stack {
    int[] arr;
    int len = 0;
    public Stack(){
        arr = new int[100];
    }
    public Stack(int n){
        arr = new int[n];
    }

    public int size(){
        return len;//数组长度
    }

    public void addSize(){
        int[] b = new int[arr.length*2];
        System.arraycopy(arr,0,b,0,arr.length);//原数组，起始位置，目标数组，起始位置，复制多少个元素
        arr = b;
    }

    public void print(){
        for(int i = 0 ; i<len ; i++){
            System.out.print(arr[i]+"   ");
        }
        System.out.println();
    }

    public void push(int a){
        if(len>=arr.length) addSize();
        arr[len] = a;
        len++;
    }

    public int pop(){
        if (len == 0){
            System.out.println("栈为空");
            return -1;
        }
        int a = arr[len-1];
        arr[len-1] = 0;
        len--;
        return a;
    }
}
