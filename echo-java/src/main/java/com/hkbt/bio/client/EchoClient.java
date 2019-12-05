package com.hkbt.bio.client;

import com.hkbt.info.ServerInfo;
import com.hkbt.util.InputUtil;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
        public static void main(String[] args) throws IOException {
            Socket socket = new Socket(ServerInfo.HOST_NAME,ServerInfo.HOST_PORT);
            Scanner scanner = new Scanner(socket.getInputStream());
            PrintStream printStream = new PrintStream(socket.getOutputStream());
            boolean flag = true;
            while (flag){
                String str = InputUtil.getString("要发送的内容：");
                printStream.println(str);
                if(scanner.hasNext()){
                    str = scanner.next();
                    System.out.println(str);
                }
                if("byebye".equalsIgnoreCase(str)){
                    printStream.println("byebye");
                    flag = false;
                }
            }
            scanner.close();
            printStream.close();
            socket.close();
        }
}
