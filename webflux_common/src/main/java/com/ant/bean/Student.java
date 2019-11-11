package com.ant.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "t_student")//指定在Mongo DB中生成的表
public class Student {
    /**
     * Mongo DB中的主键一般是字符串类型
     */
    @Id
    private String id;
    private String name;
    private int age;
}
