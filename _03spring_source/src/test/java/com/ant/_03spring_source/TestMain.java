package com.ant._03spring_source;

import com.ant.capter09.bean.User;
import com.ant.capter09.proxy.ProxyUtil;
import com.ant.capter09.service.UserService;
import com.ant.capter09.config.SpringConfig1;
import com.ant.capter09.service.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author xuchuanliangbt
 * @title: TestMain
 * @projectName kaikebaStudy
 * @description:
 * @date 2019/7/1820:05
 * @Version
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringConfig1.class)
public class TestMain {

    @Resource
    private UserService userService;
    @Resource
    private User user;

    @Test
    public void test1() {
        System.out.print(user);
        System.out.print(userService);
    }

    @Test
    public void test2() {
        UserService userService = new UserServiceImpl();
        UserService userService1 = ProxyUtil.jdkProxy(userService);
        UserService userService2 = ProxyUtil.cglibProxy(UserServiceImpl.class);
        System.out.println(userService1);
        System.out.println(userService2);
        userService1.save();
        userService2.save();
    }
}
