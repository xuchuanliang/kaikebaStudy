package com.ant.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(Springconfig2.class)
@Configuration
@ComponentScan("com.ant")
public class SpringConfig1 {
    public SpringConfig1(){
//        System.out.print("1初始化");
    }
}
