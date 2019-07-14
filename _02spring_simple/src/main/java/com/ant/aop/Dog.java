package com.ant.aop;

/**
 * 小狗
 */
public class Dog implements BaseService{
    @Override
    public void eat() {
        System.out.println("啃骨头");
    }

    @Override
    public void wc() {
        System.out.println("嘘嘘");
    }
}
