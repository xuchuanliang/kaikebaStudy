package com.hkbt.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 实现数据的
 */
public class InputUtil {
    private static final BufferedReader BUFFERED_READER = new BufferedReader(new InputStreamReader(System.in));

    private InputUtil() {
    }

    /**
     * 实现键盘数据的输入操作，可以返回的数据类型是String
     * @param hello 提示信息
     * @return
     */
    public static String getString(String hello) {
        System.out.println(hello);
        boolean flag = true;
        String str = null;
        while (flag) {
            try {
                str = BUFFERED_READER.readLine();
                if (str == null || "" == str) {
                    System.out.println("您输入的数据有误");
                } else {
                    flag = false;
                }
            } catch (IOException e) {
                System.out.println("您输入的数据有误");
            }
        }
        return str;
    }
}
