package com.qitai.demo;

import java.lang.reflect.Field;

public class AnnotationDispose {

    public static void getAnnotationClassInfo(Class<?> anClass){
        System.out.print("调用方法");
        Field[] fields = anClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(AnnotationOne.class)){
                AnnotationOne anOne = field.getAnnotation(AnnotationOne.class);
                System.out.println("当前学习目的："+anOne.value());
            }else if(field.isAnnotationPresent(AnnotationTwo.class)){
                AnnotationTwo anTwo = field.getAnnotation(AnnotationTwo.class);
                System.out.println("练习时间："+anTwo.annotationTwo());
            }else if (field.isAnnotationPresent(AnnotationThree.class)){
                AnnotationThree anTh = field.getAnnotation(AnnotationThree.class);
                System.out.println("难度系数："+anTh.id()+"  学习者："+anTh.name()+" 教程来源："+anTh.address());
            }
        }
    }
}
