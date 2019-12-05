package com.ant.nio.client;

import com.ant.nio.util.InputUtil;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOEchoClient {
    public static void main(String[] args)throws Exception{
        //打开客户端链接通道
        SocketChannel socketChannel = SocketChannel.open();
        //连接
        socketChannel.connect(new InetSocketAddress(9999));
        //开辟空间
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        boolean flag = true;
        while (flag){
            byteBuffer.clear();//清空缓冲区
            String s = InputUtil.getString("请输入你想说的话：");
            byteBuffer.put(s.getBytes());//将要输入的内容填入缓冲区
            byteBuffer.flip();//重置缓冲区
            socketChannel.write(byteBuffer);
            byteBuffer.clear();//在读取之前清空缓冲区
            int readCount = socketChannel.read(byteBuffer);
            byteBuffer.flip();
            System.err.println(new String(byteBuffer.array(),0,readCount));
            if("byebye".equalsIgnoreCase(s)){
                flag = false;
            }
        }
        socketChannel.close();
    }
}
