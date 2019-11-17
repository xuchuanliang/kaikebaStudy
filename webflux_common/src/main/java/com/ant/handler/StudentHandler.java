package com.ant.handler;

import com.ant.bean.Student;
import com.ant.repository.StudentRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * 以router function的形式编写webflux
 * 处理器
 */
@Component
public class StudentHandler {
    @Resource
    private StudentRepository studentRepository;
    /**
     * 以router function形式编写web flux
     * 1.类上使用@componet，不需要使用@Controller
     * 2.返回值全部是Mono<ServerResponse>，形参必须是ServerRequest，这两个是spring定义的请求和相应类，兼容servlet(3.1)以上的容器/jetty/netty容器
     * 3.整个以流式编程编写
     * 4.底层就是jdk9中的发布者订阅者模式（Flow.Publisher,Flow.Subscriber）
     * @param request
     * @return
     */
    public Mono<ServerResponse> findAllHandler(ServerRequest request){
        return ServerResponse
                //指定响应码
                .ok()
                //指定响应体中的响应格式内容
                .contentType(MediaType.APPLICATION_JSON)
                //响应体设置终止方法，构建响应体
                .body(studentRepository.findAll(), Student.class);
    }

    public Mono<ServerResponse> findById(ServerRequest serverRequest){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(studentRepository.findAll(),Student.class);
    }

    public Mono<ServerResponse> saveHandler(ServerRequest request){
        Mono<Student> student = request.bodyToMono(Student.class);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(studentRepository.saveAll(student),Student.class);
    }

    public Mono<ServerResponse> delHandler(ServerRequest request){
        String id = request.pathVariable("id");
        return studentRepository.findById(id).flatMap(stu->studentRepository.delete(stu).then(ServerResponse.ok().build())).switchIfEmpty(ServerResponse.notFound().build());
    }
}
