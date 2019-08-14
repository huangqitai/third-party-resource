package com.qitai.collection;

import org.junit.Test;

import java.util.*;

public class UseCollection {

    @Test
    public void UseList(){
        List<String> A = new ArrayList<>();//ArrayList和LinkedList都是List的实现类
        Collection<String> c = new LinkedList<>();//Linked是链表实现的，可以方便的操作头尾元素
        c.add("qqq");
        c.add("qqq");
        c.add("qqq");
        c.add("qqq");
        c.add("qqq");
        c.add("qqq");

//遍历集合通常使用foreach和迭代器Iterator，常规for循环比较麻烦
        A.addAll(c);

        Iterator<String> i = A.iterator();

        while (i.hasNext()){
            System.out.print(i.next()+" ");
        }

        System.out.println();

        LinkedList<String> L = new LinkedList<>();

        L.addAll(c);

        L.addFirst("wq");

        for (String l:L) {
            System.out.print(l+"    ");
        }

        System.out.println("下面是lambda迭代List");
        L.forEach(s -> {System.out.print(s);});

        /*ArrayList<Integer> a = new ArrayList<>();
        //ArrayList和Vector很像，说是Vector跟安全，但是重量级组件zhe
        Vector<Integer> v = new Vector<>();*/


    }

    @Test
    public void UseCustomQueue(){
        LinkedListRealizeQueue<Integer> l = new LinkedListRealizeQueue<>();
        l.add(21);
        l.add(23);
        l.add(23);
        l.add(23);
        l.add(23);

        System.out.println(l.peek());
    }

    @Test
    public void UseSet(){
        Set<String> set = new HashSet<>();//通过new实现类创建一个Set集合，Set是无序的
        set.add("asaa");
        set.add("asab");
        set.add("asac");

        Iterator<String> i = set.iterator();
        while (i.hasNext()){
            System.out.print(i.next()+"    ");
        }
    }

    class E implements Comparable{
        @Override
        public int compareTo(Object o) {
            return 0;
        }
    }

    /*
    自然排序
     */
    @Test
    public void UseTreeSet(){
        //TreeSet集合的排序，需要元素对象实现Comparable接口，重写CompareTo实现，因为String类型本身就实现了该接口，所以一这里不用。
        TreeSet<String> ts = new TreeSet<>();
        ts.add("a");
        ts.add("b");
        ts.add("c");
        System.out.print(ts);

       /* TreeSet<E> es = new TreeSet<>();
        es.add(new E());
        如果E对象没有实现Comparable接口，则会报错，无法排序
        */
    }

    /*
    定制排序
    需要关联一个Comparator对象，用它的compare方法自定义排序方式
     */

    class P{
            int high;
            public P(int high){
                this.high = high;
            }

            @Override
            public String toString() {
                return "P[high:"+high+"]";
            }
        }

        //这就是需要关联的自定义排序类
        class MyCompare implements Comparator{
            @Override
            public int compare(Object o1, Object o2) {
                P p1 = (P) o1;
                P p2 = (P) o2;
                //三目运算，前者大返回1，后者大返回-1，相等返回0
                return p1.high > p2.high ? 1 : p1.high < p1.high ? -1 : 0;
            }
    }

    @Test
    public void TestTreeSet(){
        TreeSet tp = new TreeSet(new MyCompare());
        tp.add(new P(123));
        tp.add(new P(156));
        tp.add(new P(157));
        tp.add(new P(111));


        System.out.print(tp);
    }

}
