package com.qitai.demo;

public class AnnotationClass {

    @AnnotationOne(value = "注解的使用")
    private String tryName;

    @AnnotationTwo(annotationTwo= AnnotationTwo.Time.Afternoon)
    private String tryTime;

    @AnnotationThree(id=90,name = "黄启太",address = "博客园")
    private String tryInfo;

    public String getTryName() {
        return tryName;
    }

    public void setTryName(String tryName) {
        this.tryName = tryName;
    }

    public String getTryTime() {
        return tryTime;
    }

    public void setTryTime(String tryTime) {
        this.tryTime = tryTime;
    }

    public String getTryInfo() {
        return tryInfo;
    }

    public void setTryInfo(String tryInfo) {
        this.tryInfo = tryInfo;
    }

    public void outTryName(){
        System.out.print("练习目的："+tryName);
    }
}
