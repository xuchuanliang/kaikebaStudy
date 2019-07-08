package com.ant.bean;

public class TeacheFactor {
    public Teacher createTeacher(){
        System.out.println("工厂类方法");
        return new Teacher();
    }
}
