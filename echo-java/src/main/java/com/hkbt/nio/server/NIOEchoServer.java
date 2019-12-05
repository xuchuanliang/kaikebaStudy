package com.hkbt.nio.server;

import com.hkbt.info.ServerInfo;

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

/**
 * java nio实现ECHO基本模型
 */
public class NIOEchoServer {

    private static class EchoClientHandler implements Runnable{

        //客户端通道
        private SocketChannel socketChannel;
        //循环处理标记
        private boolean flag = true;
        public EchoClientHandler(SocketChannel socketChannel){
            this.socketChannel = socketChannel;
            //连接成功
        }

        @Override
        public void run() {
            //1024个缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            try{
                //需要不断的交互
                while (this.flag){
                    //清空缓冲区
                    byteBuffer.clear();
                    //向缓冲区中读取数据
                    int read = this.socketChannel.read(byteBuffer);
                    String readMessage = new String(byteBuffer.array(),0,read).trim();
                    String writeMessage = "[ECHO] "+readMessage;
                    if("byebye".equalsIgnoreCase(readMessage)){
                        writeMessage = "[EXIT] 拜拜，下次再见";
                        flag=false;
                    }
                    //数据输入通过缓存的形式完成，而数据的输出同样需要进行缓存操作
                    byteBuffer.clear();//为了写入新的数据而定义
                    System.out.println(writeMessage);
                    byteBuffer.put(writeMessage.getBytes());//发送内容
                    byteBuffer.flip();//重置缓冲区
                    this.socketChannel.write(byteBuffer);
                }
                this.socketChannel.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception{
        //1.NIO的实现考虑到性能问题以及响应时间问题，需要设置一个线程池，采用固定大小的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        //2.NIO的处理是基于Channel控制的，所以有一个Selector就是负责管理所有的Channel
        ServerSocketChannel channel = ServerSocketChannel.open();
        //3.需要为其设置一个非阻塞的状态机制
        channel.configureBlocking(false);
        //4.服务器上需要提供一个网络的监听端口
        channel.bind(new InetSocketAddress(ServerInfo.HOST_PORT));
        //5.需要设置一个Selector，作为一个选择器出现，目的是管理所有的channel
        Selector selector = Selector.open();
        //6.将当前channel注册到Selector之中
        //连接时处理
        channel.register(selector, SelectionKey.OP_ACCEPT);
        //7.NIO采用轮询方式，每当发现有用户连接的时候就需要启动一个线程（线程池管理）
        int keySelect = 0;//接收轮询状态
        System.out.println("EchoServer启动成功，监听端口是："+ServerInfo.HOST_PORT);
        while ((keySelect = selector.select())>0){//实现轮询处理
            Set<SelectionKey> selectionKeys = selector.selectedKeys();//获取全部的key
            Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
            while (selectionKeyIterator.hasNext()){
                SelectionKey selectionKey = selectionKeyIterator.next();//获取每一个key的信息
                if (selectionKey.isAcceptable()){//是连接模式
                    //等待连接
                    SocketChannel clientChannel = channel.accept();
                    if(clientChannel!=null){
                        executorService.submit(new EchoClientHandler(clientChannel));
                    }
                }
                selectionKeyIterator.remove();
            }
        }
        executorService.shutdown();
        channel.close();
    }
}
