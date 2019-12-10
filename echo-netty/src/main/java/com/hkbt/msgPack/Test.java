package com.hkbt.msgPack;

import com.hkbt.objectEncode.Person;
import org.msgpack.MessagePack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception{
        List<Person> personList = new ArrayList<Person>();
        for(int i=0;i<10;i++){
            Person p = new Person("snail"+i,20,"man"+i,2.2);
            personList.add(p);
        }
        serJava(personList);
        serMsgPack(personList);
    }
    public static void serJava(List<Person> personList) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(personList);
        System.out.println("java原生序列化："+byteArrayOutputStream.toByteArray().length);
    }

    public static void serMsgPack(List<Person> personList) throws IOException {
        MessagePack messagePack = new MessagePack();
        System.out.println("messagePack序列化："+messagePack.write(personList).length);

    }

}
