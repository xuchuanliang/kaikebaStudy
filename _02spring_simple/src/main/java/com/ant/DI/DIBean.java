package com.ant.DI;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 依赖注入
 */
@Data
@ToString
public class DIBean {
    private String teacherName;
    private String[] friendArray;
    private List<String> school;
}
