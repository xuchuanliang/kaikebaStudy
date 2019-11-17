package com.ant;

import com.ant.handler.StudentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import javax.annotation.Resource;

@Configuration
public class Router {
    @Resource
    private StudentHandler studentHandler;

    @Bean
    public RouterFunction<ServerResponse> responseRouterFunction() {
        return RouterFunctions
                //nest方法中用于定义各种路由规则，即uri到处理器函数的映射关系
                .nest(
                        RequestPredicates.path("/student"),
                        //查找路由，将/student/all的get请求路由到studentHandler的findAllHandler方法
                        RouterFunctions.route(RequestPredicates.GET("/all"), studentHandler::findAllHandler)
                                //查找路由，将/student/findById的get请求路由到studentHandler的findBYId方法，并且要求请求类型是x-www-form-urlencoded
                                .andRoute(RequestPredicates.GET("/findById").and(RequestPredicates.accept(MediaType.APPLICATION_FORM_URLENCODED)),
                                        studentHandler::findById)
                                .andRoute(RequestPredicates.POST("/saveHandler").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),studentHandler::saveHandler)
                                .andRoute(RequestPredicates.GET("/delHandler"),studentHandler::delHandler)

                );
    }
}
