package com.ant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories
@SpringBootApplication
public class WebfluxCommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxCommonApplication.class, args);
    }

}
