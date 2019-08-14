package com.qitai.collection;

import org.junit.Test;

import java.util.Comparator;
import java.util.TreeSet;

public class TestTreeSet {
    @Test
    public void TestTreeSet(){
        TreeSet tp = new TreeSet(new MyCompare());
        tp.add(new M(111));
        tp.add(new M(123));
        tp.add(new M(156));
        tp.add(new M(157));

        System.out.print(tp);
    }

    @Test
    public void LambdaTreeSet(){
       /* TreeSet tm = new TreeSet(new MyCompare(){

            @Override
            public int compare(Object o1, Object o2) {
                return super.compare(o1, o2);
            }
        });*/

        TreeSet tm = new TreeSet((Object o1,Object o2) -> {return 1;});

        tm.add(new M(123));
        tm.add(new M(156));
        tm.add(new M(157));
        tm.add(new M(111));

        System.out.print(tm);
    }
}
class M implements Comparable{
    int high;
    public M(){

    }
    public M(int high){
        this.high = high;
    }

    @Override
    public String toString() {
        return "P[high:"+high+"]";
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}

//这就是需要关联的自定义排序类
class MyCompare implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        M p1 = (M) o1;
        M p2 = (M) o2;
        //三目运算，前者大返回1，后者大返回-1，相等返回0
        return p1.high > p2.high ? 1 : p1.high < p1.high ? -1 : 0;
    }
}