package com.example.demo.utils.jna;


import com.sun.jna.ptr.ShortByReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zj
 * @date 2020/5/9
 */
public class CtidUtils {

    private static Logger logger = LoggerFactory.getLogger(CtidUtils.class);

    private final static String SUCCESS = "0";
    private final static short BTIME = 100;
    private final static String appId = "0";
    private final static String organizeId = "0";
    /**
     * 获取二维码申请数据
     *
     * @return
     */
    public static String getApplyData() throws Exception {
        CtidLibrary.ApplyData.ByReference apply = new CtidLibrary.ApplyData.ByReference();
        apply.appId = appId.getBytes();
        apply.organizeId = organizeId.getBytes();
        apply.appIdLen = 4;
        apply.organizeIdLen = 8;
        apply.type = 1;
        byte[] applyData = new byte[1024];
        ShortByReference applyDataLen = new ShortByReference();
        byte a = CtidLibrary.INSTANCE.getApplyData(apply, applyData, applyDataLen);
        String resp = "" + a;
        if (!resp.equals(SUCCESS)) {
            logger.info("appId:" + appId);
            logger.info("appId:" + organizeId);
            logger.error("获取二维码申请数据失败，原因:" + resp);
            throw new Exception("获取二维码申请数据失败，原因:" + resp) ;
        }
        String result = new String(applyData);
        logger.info("获取二维码申请数据成功: " + result);
        return result.trim();
    }

    /**
     * 获取网证密码
     *
     * @param randomNumber
     * @return
     */
    public static String getAuthCodeData(String randomNumber) throws Exception {
        short randomNumberLen =(short)randomNumber.length();
        byte[] authCode = new byte[1024];
        ShortByReference authCodeLen = new ShortByReference();
        byte a = CtidLibrary.INSTANCE.getAuthCodeData(randomNumber, randomNumberLen, BTIME, authCode, authCodeLen);
        String resp = "" + a;
        if (!resp.equals(SUCCESS)) {
            throw new Exception("获取网证密码失败，原因:" + resp) ;
        }
        String result = new String(authCode);
        logger.info("获取网证密码成功: " + result);
        return result.trim();
    }

    /**
     * 获取二维码验码数据
     *
     * @param randomNumber
     * @param qrCode
     * @return
     */
    public static String getAuthQRCodeData(String randomNumber, String qrCode) throws Exception {
        short randomNumberLen =(short)randomNumber.length();
        CtidLibrary.QRCodeData.ByReference QRCode = new CtidLibrary.QRCodeData.ByReference();
        QRCode.appId = appId.getBytes();
        QRCode.organizeId = organizeId.getBytes();
        QRCode.appIdLen = 4;
        QRCode.organizeIdLen = 8;
        byte[] qrCodea = qrCode.getBytes();
        for (int i = 0; i < qrCodea.length; i++) {
            QRCode.qrCode[i] = qrCodea[i];
        }
        QRCode.qrCodeLen = (short) qrCode.length();
        byte[] qrData = new byte[2048];
        ShortByReference qrDataLen = new ShortByReference();
        byte a = CtidLibrary.INSTANCE.getAuthQRCodeData(randomNumber, randomNumberLen, QRCode, qrData, qrDataLen);
        String resp = "" + a;
        if (!resp.equals(SUCCESS)) {
            throw new Exception("获取二维码验码数据失败，原因:" + resp) ;

        }
        String result = new String(qrData);
        logger.info("获取二维码验码数据成功: " + result);
        return result.trim();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getApplyData());
    }
}
