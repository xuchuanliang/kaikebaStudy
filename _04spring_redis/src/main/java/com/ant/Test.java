package com.ant;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author xuchuanliangbt
 * @title: Test
 * @projectName kaikebaStudy
 * @description:
 * @date 2019/11/19:32
 * @Version
 */
public class Test {
    static String utf8 = "utf-8";
    static String iso = "iso-8859-1";
    static String gbk = "GBK";

    public static void main(String[] args) throws UnsupportedEncodingException {
        String str = "浙A88888";
//              System.out.println("?的十六进制为：3F");
//              System.err
//                      .println("出现中文时，如果编码方案不支持中文，每个字符都会被替换为？的对应编码！（如在iso-8859-1中）");
//        System.out.println("原始字符串：\t\t\t\t\t\t" + str);
//        String utf8_encoded = URLEncoder.encode(str, "utf-8");
//        System.out.println("用URLEncoder.encode()方法,并用UTF-8编码后:\t\t" + utf8_encoded);
//        String gbk_encoded = URLEncoder.encode(str, "GBK");
//        System.out.println("用URLEncoder.encode()方法,并用GBK编码后：\t\t" + gbk_encoded);
//        testEncoding(str, utf8, gbk);
//        testEncoding(str, gbk, utf8);
//        testEncoding(str, gbk, iso);
//        printBytesInDifferentEncoding(str);
//        printBytesInDifferentEncoding(utf8_encoded);
//        printBytesInDifferentEncoding(gbk_encoded);
        //获取乱码   ?A88888
        String s = new String(str.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        //获取iso-8859-1字节数组
        byte[] ISO = s.getBytes(StandardCharsets.ISO_8859_1);
        System.out.println(new String(ISO, "8859_1"));
    }

    /**
     * 测试用错误的编码方案解码后再编码，是否对原始数据有影响
     *
     * @param str             输入字符串，Java的String类型即可
     * @param encodingTrue    编码方案1，用于模拟原始数据的编码
     * @param encondingMidian 编码方案2，用于模拟中间的编码方案
     * @throws UnsupportedEncodingException
     */
    public static void testEncoding(String str, String encodingTrue,
                                    String encondingMidian) throws UnsupportedEncodingException {
        System.out.println();
        System.out
                .printf("%s编码的字节数据->用%s解码并转为Unicode编码的JavaString->用%s解码变为字节流->读入Java(用%s解码)后变为Java的String\n",
                        encodingTrue, encondingMidian, encondingMidian,
                        encodingTrue);
        System.out.println("原始字符串：\t\t" + str);
        byte[] trueEncodingBytes = str.getBytes(encodingTrue);
        System.out.println("原始字节流：\t\t" + bytesToHexString(trueEncodingBytes)
                + "\t\t//即用" + encodingTrue + "编码后的字节流");
        String encodeUseMedianEncoding = new String(trueEncodingBytes,
                encondingMidian);
        System.out.println("中间字符串：\t\t" + encodeUseMedianEncoding + "\t\t//即用"
                + encondingMidian + "解码原始字节流后的字符串");
        byte[] midianBytes = encodeUseMedianEncoding.getBytes("Unicode");
        System.out.println("中间字节流：\t\t" + bytesToHexString(midianBytes)
                + "\t\t//即中间字符串对应的Unicode字节流(和Java内存数据一致)");
        byte[] redecodedBytes = encodeUseMedianEncoding
                .getBytes(encondingMidian);
        System.out.println("解码字节流：\t\t" + bytesToHexString(redecodedBytes)
                + "\t\t//即用" + encodingTrue + "解码中间字符串(流)后的字符串");
        String restored = new String(redecodedBytes, encodingTrue);
        System.out.println("解码字符串：\t\t" + restored + "\t\t和原始数据相同？  "
                + restored.endsWith(str));
    }

    /**
     * 将字符串分别编码为GBK、UTF-8、iso-8859-1的字节流并输出
     *
     * @param str
     * @throws UnsupportedEncodingException
     */
    public static void printBytesInDifferentEncoding(String str)
            throws UnsupportedEncodingException {

        String s = "???";
        new String(s.getBytes("UTF-8"), "GBK");

        System.out.println("");
        System.out.println("原始String:\t\t" + str + "\t\t长度为：" + str.length());
        String unicodeBytes = bytesToHexString(str.getBytes("unicode"));
        System.out.println("Unicode bytes:\t\t" + unicodeBytes);
        String gbkBytes = bytesToHexString(str.getBytes("GBK"));
        System.out.println("GBK bytes:\t\t" + gbkBytes);
        String utf8Bytes = bytesToHexString(str.getBytes("utf-8"));
        System.out.println("UTF-8 bytes:\t\t" + utf8Bytes);
        String iso8859Bytes = bytesToHexString(str.getBytes("iso-8859-1"));
        System.out.println("iso8859-1 bytes:\t" + iso8859Bytes + "\t\t长度为："
                + iso8859Bytes.length() / 3);
        System.out.println("可见Unicode在之前加了两个字节FE FF，之后则每个字符两字节");
    }

    /**
     * 将该数组转的每个byte转为两位的16进制字符，中间用空格隔开
     *
     * @param bytes 要转换的byte序列
     * @return 转换后的字符串
     */
    public static final String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xff);// &0xff是byte小于0时会高位补1，要改回0
            if (hex.length() == 1)
                sb.append('0');
            sb.append(hex);
            sb.append(" ");
        }
        return sb.toString().toUpperCase();
    }
}

