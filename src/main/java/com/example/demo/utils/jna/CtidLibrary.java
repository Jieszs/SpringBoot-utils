package com.example.demo.utils.jna;


import com.sun.jna.*;
import com.sun.jna.ptr.ShortByReference;

/**
 * ctid dll
 * 调用本地dll/so
 * linux: /usr/lib/libRobotCreateModel.so
 * win:所有DLL放同级目录
 * 依赖
 *         <dependency>
 *             <groupId>net.java.dev.jna</groupId>
 *             <artifactId>jna</artifactId>
 *             <version>5.5.0</version>
 *         </dependency>
 * 1.dll 32位 jdk也要 32 位
 * 2.char * 入参 对应 String ,出参 对应 byte[]
 * 3.结构体里的参数要指定内存大小
 * 4.dll存在依赖的需要先载入依赖的dll
 *
 * @author zj
 * @date 2020/5/7
 */
public interface CtidLibrary extends Library {
    CtidLibrary INSTANCE = Native.load("IDIdentify", CtidLibrary.class);

    /**
     * 获取二维码申请数据
     *
     * @param apply        结构体
     * @param applyData    出参 二维码申请数据
     * @param applyDataLen 出参 二维码申请数据长度
     * @return
     */
    byte getApplyData(ApplyData.ByReference apply, byte[] applyData, ShortByReference applyDataLen);

    /**
     * 获取网证密码
     *
     * @param randomNumber    身份认证申请应答数据中的随机数
     * @param randomNumberLen 随机数长度
     * @param btime           输入框大小
     * @param authCode        出参  输出网证口令
     * @param authCodeLen     出参  网证口令长度
     * @return
     */
    byte getAuthCodeData(String randomNumber, short randomNumberLen, short btime, byte[] authCode, ShortByReference authCodeLen);

    /**
     * 获取二维码验码数据
     *
     * @param randomNumber    身份认证申请应答数据中的随机数
     * @param randomNumberLen 随机数长度
     * @param QRCode          QRCodeData 结构体
     * @param qrData          出参 二维码验码数据
     * @param qrDataLen       出参 二维码验码数据长度
     * @return
     */
    byte getAuthQRCodeData(String randomNumber, short randomNumberLen, QRCodeData.ByReference QRCode, byte[] qrData, ShortByReference qrDataLen);

    @Structure.FieldOrder({"type", "organizeId", "organizeIdLen", "appId", "appIdLen"})
    public static class ApplyData extends Structure {
        //操作类型 0二维码赋码 1二维码验码
        public short type;
        //机构id
        public byte[] organizeId;
        //机构id长度8
        public short organizeIdLen;
        //应用id
        public byte[] appId;
        //应用id长度4
        public short appIdLen;

        //这个类代表结构体指针
        public static class ByReference extends ApplyData implements Structure.ByReference {
        }

        //这个类代表结构体本身
        public static class ByValue extends ApplyData implements Structure.ByValue {
        }

    }

    @Structure.FieldOrder({"qrCode", "qrCodeLen", "organizeId", "organizeIdLen", "appId", "appIdLen"})
    public static class QRCodeData extends Structure {
        //二维码数据 4096,要和dll的大小一致
        public byte[] qrCode = new byte[4096];
        // 二维码数据长度 4096
        public short qrCodeLen;
        //机构id
        public byte[] organizeId;
        //机构id长度8
        public short organizeIdLen;
        //应用id
        public byte[] appId;
        //应用id长度4
        public short appIdLen;

        //这个类代表结构体指针
        public static class ByReference extends QRCodeData implements Structure.ByReference {
        }

        //这个类代表结构体本身
        public static class ByValue extends QRCodeData implements Structure.ByValue {
        }
    }
}
