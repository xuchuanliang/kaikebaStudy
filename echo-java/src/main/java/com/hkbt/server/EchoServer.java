package com.hkbt.server;

import com.hkbt.info.ServerInfo;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer {

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(ServerInfo.HOST_PORT);
        System.out.println("服务端已经启动，监听端口是 ："+ServerInfo.HOST_NAME+":"+ServerInfo.HOST_PORT);
        boolean flag = true;
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        while (flag){
            Socket client = socket.accept();
            executorService.submit(new EchoClientHander(client));
        }
        executorService.shutdown();
        socket.close();
    }

    private static class EchoClientHander implements Runnable{
        private Socket client;
        private Scanner scanner;
        private PrintStream printStream;
        private boolean flag = true;

        public EchoClientHander(Socket socket) throws IOException {
            this.client = socket;
            this.scanner = new Scanner(this.client.getInputStream());
            this.printStream = new PrintStream(this.client.getOutputStream());
        }

        public void run() {
            while (flag){
                if(this.scanner.hasNext()){
                    String val = this.scanner.next().trim();
                    System.out.println("接收到客户端消息："+val);
                    if("byebye".equalsIgnoreCase(val)){
                        this.printStream.println("byebye...");
                        this.flag = false;
                    }else{
                        printStream.println(val);
                    }
                }
            }
            this.scanner.close();
            this.printStream.close();
            try {
                this.client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
