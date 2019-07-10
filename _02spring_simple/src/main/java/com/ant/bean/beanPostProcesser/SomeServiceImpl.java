package com.ant.bean.beanPostProcesser;

public class SomeServiceImpl implements SomeService{
    @Override
    public String some() {
        return "hellow ant";
    }

    public SomeServiceImpl() {
        System.out.println("init");
    }
}
