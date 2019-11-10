package com.ant.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class SomeController {

    /**
     * 传统BIO方式请求
     * 2019-11-10 13:41:37.686 ERROR 17912 --- [ctor-http-nio-2] com.ant.controller.SomeController        : common request start
     * 2019-11-10 13:41:42.686 ERROR 17912 --- [ctor-http-nio-2] com.ant.controller.SomeController        : common request end
     * @return
     */
    @GetMapping("/commonRequest")
    public String commonRequest(){
        log.error("common request start");
        //耗时操作
        String s = common("common request");
        log.error("common request end");
        return s;
    }
    private String common(String s){
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * reactive 请求
     * 服务端同一个处理器可以处理更过的请求，但是对浏览器(客户端)的体验是一样的
     * 好处就是服务端的吞吐量、并发性得到提升
     * 2019-11-10 13:41:21.789 ERROR 17912 --- [ctor-http-nio-2] com.ant.controller.SomeController        : mono start
     * 2019-11-10 13:41:21.790 ERROR 17912 --- [ctor-http-nio-2] com.ant.controller.SomeController        : mono end
     * @return
     */
    @GetMapping("/monoRequest")
    public Mono<String> monoRequest(){
        log.error("mono start");
        //Mono表示包含0或1一个元素的异步序列
        //耗时操作
        Mono<String> mono = Mono.fromSupplier(()->mono("mono request"));
        log.error("mono end");
        return mono;
    }

    private String mono(String s){
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return s;
    }

    @GetMapping("/flux")
    public Flux<String> flux(){
        //Flux表示包含0或多个元素的异步序列
        return Flux.just("beijing","shanghai","hangzhou","nanjing");
    }

    @GetMapping("/fluxArr")
    public Flux<String> fluxArr(@RequestParam String[] cities){
        return Flux.fromArray(cities);
    }

    @GetMapping("/fluxList")
    public Flux<String> fluxList(@RequestParam List<String> cities){
        return Flux.fromStream(cities.stream());
    }
}
