package com.qitai.arithmeticexercise;

import org.junit.Test;

public class SortArray {
    @Test
    public void testSort(){
        int[] array = {4,8,6,2,7,3,9};
        sort(array);
        System.out.println();
        for (int i=0;i<array.length;i++){
            System.out.print(array[i]+"   ");
        }
    }
    public void sort(int[] array){
        for(int i=0 ;i<array.length;i++){
           for(int j=0;j<array.length-i-1;j++){
               if(array[j]>array[j+1]){
                   int temp = array[j];
                   array[j] = array[j+1];
                   array[j+1] = temp;
               }
           }
            for (int a=0;a<array.length;a++){
                System.out.print(array[a]+"   ");
            }
            System.out.println();
        }
    }

    int sum =0;
    @Test
    public void testSum(){
        int n = 7;

        System.out.println(sum(n));
    }
    public int sum(int n){
        sum = sum + n;
        int m = (n>1)?sum(n-1):1;
        //sum = sum + m;
        return sum;
    }


    @Test
    public void testException(){
        try {
            exception(new int[]{0,1,2,3,4,5});
        }catch (Exception e){
            System.out.println("E");
        }
    }
    private void exception(int[] array){
        for(int i=0;i<array.length;i++){
            try {
                if(array[i]%2!=0){
                    throw new NullPointerException();
                }
                else {
                    System.out.println(i);
                }
            }finally {
                System.out.println("e");
            }
        }
    }

    //键值对相等哈希值就相等，但哈希值相等键值对不一定相等
    @Test
    public void hashcodeTest(){
        String str1 = "通话";
        String str2 = "重地";

        System.out.println(String.format("str1:%d | str2:%d",str1.hashCode(),str2.hashCode()));
        System.out.println(str1.equals(str2));
    }
}
