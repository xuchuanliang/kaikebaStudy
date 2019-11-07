package com.ant.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Value("${name}")
    private String name;

    @GetMapping("/get")
    public String get(){
        return name;
    }
}
