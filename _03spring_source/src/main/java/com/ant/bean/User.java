package com.ant.bean;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@ToString
@Component
@PropertySource("classpath:jdbc.properties")
public class User {
    @Value("${user.name}")
    private String id;
    @Value("${user.age}")
    private String age;
}
