package com.ant.controller;

import com.ant.bean.Student;
import com.ant.repository.StudentRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * reactive stream
 * 即spring的实现是webflux
 * 使用这种开发方式返回数据只能是Mono或Flux
 * 并且webflux只支持noSQL，不支持关系型数据库
 */
@RestController
@RequestMapping("/student")
public class StudentContoller {

    @Resource
    private StudentRepository studentRepository;

    @GetMapping("/all")
    public Flux<Student> getAll(){
        return studentRepository.findAll();
    }

    /**
     * SSE方式获取数据
     * @return
     */
    @GetMapping(value = "/sse/all",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Student> getSseAll(){
        return studentRepository.findAll();
    }

    @PostMapping("/add")
    public Mono<Student> add(Student student){
        return studentRepository.save(student);
    }

    @PostMapping("/addJson")
    public Mono<Student> addJson(@RequestBody Student student){
        return studentRepository.save(student);
    }
}
