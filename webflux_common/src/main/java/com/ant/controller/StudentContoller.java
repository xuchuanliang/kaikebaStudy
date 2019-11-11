package com.ant.controller;

import com.ant.bean.Student;
import com.ant.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.ArrayList;

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

    /**
     * 新增
     * @param student
     * @return
     */
    @PostMapping("/add")
    public Mono<Student> add(Student student){
        return studentRepository.save(student);
    }

    /**
     * 新增
     * @param student
     * @return
     */
    @PostMapping("/addJson")
    public Mono<Student> addJson(@RequestBody Student student){
        return studentRepository.save(student);
    }

    /**
     * 无状态删除，即无论是否存在该id，均返回200 OK状态码
     * @param id
     * @return
     */
    @DeleteMapping("/del/{id}")
    public Mono<Void> del(@PathVariable("id") String id){
        return studentRepository.deleteById(id);
    }

    /**
     * 需求：根据id删除元素，如果未查到，则返回404状态吗，否则返回200
     * ResponseEntity可以封装响应体中的数据和响应码
     * Mono中的map()和flatMap()方法均用于元素映射，选择标准是：一般情况下映射过程中需要再执行
     * 一些操作的过程，需要选择使用flatMap()；若是仅仅元素的映射，而无需执行一些操作，则选择map()，
     * 是因为我们查看源码可知flatMap()是异步操作，map()是同步操作
     * @return
     */
    @DeleteMapping("/delStatus/{id}")
    public Mono<ResponseEntity<Void>> delStatus(@PathVariable("id") String id){
        return studentRepository.findById(id)
                .flatMap(stu->studentRepository.delete(stu).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))//如果findById查到数据，则删除，并返回200
                .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));//如果findById未查询到数据，则返回404
    }

    /**
     * 修改操作：若修改成功，则返回修改后的对象以及200状态码，否则返回404状态码
     * @param id
     * @param student
     * @return
     */
    @PutMapping("/updateStatus/{id}")
    public Mono<ResponseEntity<Student>> updateStatus(@PathVariable("id") String id,Student student){
        return studentRepository.findById(id)
                .flatMap(stu -> {
                    stu.setAge(student.getAge());
                    stu.setName(student.getName());
                    return studentRepository.save(stu);
                }).map(stu->new ResponseEntity<Student>(stu,HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<Student>(HttpStatus.NOT_FOUND));
    }
}
