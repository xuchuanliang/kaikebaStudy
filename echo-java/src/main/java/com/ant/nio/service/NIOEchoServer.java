package com.ant.nio.service;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NIOEchoServer {
    public static void main(String[] args)throws Exception{
        //1.NIO的实现考虑到性能以及相应时间问题，需要设置一个线程池，采用固定大小线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        //2.NIO的控制是基于Channel控制的，所以有一个Selector就是负责管理所有的Channel
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        //3.需要为其设置一个非阻塞的状态机制
        socketChannel.configureBlocking(false);
        //4.绑定到本级的9999端口
        socketChannel.bind(new InetSocketAddress(9999));
        //5.需要设置一个Selector，作为一个选择器出现，目的是管理所有的Channel
        Selector selector = Selector.open();
        //6.将当前的Channel注册到Selector中，并设置连接时处理
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.err.println("启动成功，监听端口是9999");
        //7.NIO采用轮询的模式，每当发现有用户连接的时候就需要启动一个线程
        while (selector.select()>0){
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                if(selectionKey.isAcceptable()){//为链接模式
                    SocketChannel channel = socketChannel.accept();
                    if(null!=channel){
                        executorService.submit(new EchoClientHandler(channel));
                    }
                }
                iterator.remove();
            }
        }
        socketChannel.close();
        executorService.shutdown();
    }
    private static class EchoClientHandler implements Runnable{
        //客户端通道
        private SocketChannel socketChannel;
        private boolean flag = true;
        public EchoClientHandler(SocketChannel socketChannel){
            this.socketChannel = socketChannel;
            System.out.println("客户端链接成功");
        }

        @Override
        public void run() {
            //开辟一个1024字节的Buffer空间
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            try{
                //不断通过管道与客户端进行交互
                while (flag){
                    //清空缓冲区
                    byteBuffer.clear();
                    //从缓冲区中读取数据
                    int read = socketChannel.read(byteBuffer);
                    String readMessage = new String(byteBuffer.array(),0,read).trim();
                    System.out.println("获取到客户端的废话："+readMessage);
                    String writeMessage = "[ECHO]back:"+ readMessage+"\n";
                    if("byebye".equalsIgnoreCase(readMessage)){
                        writeMessage = "[ECHO]back：再见了";
                        flag = false;
                    }
                    byteBuffer.clear();//为了写入新的数据进行buffer清空
                    byteBuffer.put(writeMessage.getBytes());
                    byteBuffer.flip();//重置缓冲区的position和limit
                    socketChannel.write(byteBuffer);//回应数据
                }
                socketChannel.close();
            }catch (Exception e){

            }
        }
    }
}
