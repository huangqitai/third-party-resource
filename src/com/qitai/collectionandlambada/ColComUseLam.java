package com.qitai.collectionandlambada;

import org.junit.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;

public class ColComUseLam {

    @Test
    public void testColComULam(){

        /*
        这种写法是使用实体类中实现了Comparable接口，重写compareTo方法完成排序
        Person::compareTo这种写法表示调用Person的compareTo方法，有点C++里面的感觉
        TreeSet<Person> tp = new TreeSet<>(Person::compareTo);
         */

        /*
        这是使用匿名内部类方式排序TreeSet
        TreeSet<Person> tp = new TreeSet<>(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getAge() > o2.getAge() ? 1 : o1.getAge() < o2.getAge() ? -1 : 0;
            }
        });*/

        /*
        以下使用Lambda代替匿名内部类排序
         */
       TreeSet<Person> tp = new TreeSet<>((Person o1,Person o2) -> o1.getAge() > o2.getAge() ? 1 : o1.getAge() < o2.getAge() ? -1 : 0);

        tp.add(new Person(23,"ssa"));
        tp.add(new Person(22,"sa"));
        tp.add(new Person(21,"sgga"));
        tp.add(new Person(24,"a"));
        tp.add(new Person(20,"ssasd"));

       /*
       这种写法排序我觉得是可行的，也可以使用Lambda表达式，但是只用于List
       Collections.sort(tp, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.compareTo(o2);
            }
        });*/

        /*Lambda表达式遍历集合,代替了以下forEach遍历方式，
        for (Person p : tp ) {
            System.out.println(p.toString());
        }*/
        tp.forEach(person -> System.out.println(person.toString()));

    }
}
