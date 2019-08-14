package com.qitai.collectionandlambada;

public class Person implements Comparable<Person>{

    private Integer age;
    private String name;

    public Person(Integer age, String name) {
        this.age = age;
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name+"  "+this.getAge();
    }

    @Override
    public int compareTo(Person o) {
        if(this.age>o.getAge()){
            return 1;
        }else if(this.age<o.getAge()){
            return -1;
        }else return 0;
    }
}
