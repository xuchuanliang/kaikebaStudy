package com.ant.service;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    public UserServiceImpl(){
        System.out.print("userServiceIMple");
    }

    @Override
    public void save(){
        System.out.println("save");
    }

    @Override
    public void update(){
        System.out.println("update");
    }
}
