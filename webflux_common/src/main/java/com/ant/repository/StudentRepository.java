package com.ant.repository;

import com.ant.bean.Student;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface StudentRepository extends ReactiveMongoRepository<Student,String> {
    Flux<Student> findByAgeBetween(int below, int top);
}
