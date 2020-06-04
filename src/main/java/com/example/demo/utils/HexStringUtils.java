package com.example.demo.utils;

/**
 * 十六进制字符串工具类
 *
 * 十六进制字符串转为字符串会出现乱码
 * 1.编码不对
 * 2.十六进制字符串需要转为byte之后进行加密
 */
public class HexStringUtils {

    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 16进制字符串转十进制字节数组
     * 这是常用的方法，如某些硬件的通信指令就是提供的16进制字符串，发送时需要转为字节数组再进行发送
     *
     * @param strSource 16进制字符串，如 "455A432F5600"，每两位对应字节数组中的一个10进制元素
     *                  默认会去除参数字符串中的空格，所以参数 "45 5A 43 2F 56 00" 也是可以的
     * @return 十进制字节数组, 如 [69, 90, 67, 47, 86, 0]
     */
    public static byte[] hexString2Bytes(String strSource) {
        if (strSource == null || "".equals(strSource.trim())) {
            System.out.println("hexString2Bytes 参数为空，放弃转换.");
            return null;
        }
        strSource = strSource.replace(" ", "");
        int l = strSource.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            ret[i] = Integer.valueOf(strSource.substring(i * 2, i * 2 + 2), 16).byteValue();
        }
        return ret;
    }

    /**
     * 方法二：
     * byte[] to hex string
     *
     * @param bytes
     * @return
     */
    public static String bytesToHexFun2(byte[] bytes) {
        char[] buf = new char[bytes.length * 2];
        int index = 0;
        for (byte b : bytes) { // 利用位运算进行转换，可以看作方法一的变种
            buf[index++] = HEX_CHAR[b >>> 4 & 0xf];
            buf[index++] = HEX_CHAR[b & 0xf];
        }

        return new String(buf);
    }

    public static void main(String[] args) {
        String hexString = "00 F3 01 03 47 08 18 4C 00 E6 43 11 A8 08 CF 2E F8 C0 7F 78 05 34 38 66 35 32 36 62 33 64 38 30 39 34 37 35 63 62 35 61 36 33 34 32 30 62 30 32 34 34 66 37 61 30 30 30 30 31 30 30 31 32 30 32 30 30 35 32 30 32 30 32 30 31 31 32 30 05 59 C0 A9 D9 77 69 05 23 8E 06 87 F0 F6 D1 32 5B 78 98 D0 59 C0 A8 D1 E1 D6 24 ED F0 55 02 36 4B B1 FC 46 6B 55 FA 1A FF 05 48 85 37 5C D6 96 E0 86 85 D8 84 00 A1 16 12 65 2B FF 62 6B CD 7E D7 78 5C A7 2C E8 C7 46 09 7F 20 EA 25 05 FB 13 7A 21 24 06 FD 6F 46 E3 77 86 51 D9 38 30 45 02 20 74 23 FA 3E 4F F0 ED 15 28 9F 4D 3B 38 16 19 DC A3 76 86 6B B9 EE 2A 48 35 44 22 30 24 90 A1 02 02 21 00 E5 75 4E E1 D4 19 3F 96 DB 52 19 BC 7C 73 38 DC C4 C1 73 D5 ED 67 0E 2B D8 D0 52 B4 8C 61 C9 FE 00 00 2C 00 60 01 04 4E 4C 50 54 FF FF FF FF FF FF FF FF 8E E9 6C 6E 2A FB 91 05 01 49 42 94 2F 80 1B 95 A6 B4 7C 8A 75 2A B4 56 D4 D9 4E A9 14 34 BF AD D0 F6 75 15 9D 68 36 A7 52 C2 F2 14 9A A2 F7 32 C7 DD FF 99 CF DE 3C F9 67 48 9F BA C2 A2 19 D1 2A F8 C7 A0 55 69 C3 6A FD 20 CB E5 78 CB 44 C9 3B ";
        System.out.println(bytesToHexFun2(hexString2Bytes(hexString)));
    }
}
