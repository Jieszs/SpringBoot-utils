package com.example.demo.utils;


import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
/**
 * 识别二维码
 *
 * 1.依赖
 *
 *         <dependency>
 *             <groupId>com.google.zxing</groupId>
 *             <artifactId>javase</artifactId>
 *             <version>3.2.1</version>
 *         </dependency>
 *
 *         <dependency>
 *             <groupId>com.google.zxing</groupId>
 *             <artifactId>core</artifactId>
 *             <version>3.3.3</version>
 *         </dependency>
 */
public class QRCodeUtils {

    /**
     * 解析二维码解析,此方法是解析Base64格式二维码图片
     * baseStr:base64字符串,data:image/png;base64开头的
     */
    public static String deEncodeByBase64(String baseStr) {
        String content = null;
        BufferedImage image;
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = null;
        try {
            b = decoder.decodeBuffer(baseStr);//baseStr转byte[]
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(b);//byte[] 转BufferedImage
            image = ImageIO.read(byteArrayInputStream);
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);//解码
            log.info("图片中内容： " + result.getText());
            content = result.getText();
        } catch (IOException e) {
            log.error(e.getMessage(),e.getStackTrace());
        } catch (NotFoundException e) {
            log.error(e.getMessage(),e.getStackTrace());
        }
        return content;
    }

    /**
     * 解析二维码,此方法解析一个路径的二维码图片
     * path:图片路径
     */
    public static String deEncodeByPath(String path) {
        String content = null;
        BufferedImage image;
        try {
            image = ImageIO.read(new File(path));
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);//解码
            log.info("图片中内容： " + result.getText());
            content = result.getText();
        } catch (IOException e) {
            log.error(e.getMessage(),e.getStackTrace());
        } catch (NotFoundException e) {
            log.error(e.getMessage(),e.getStackTrace());
        }
        return content;

    }

    public static void main(String[] args) {
        String base64Str = ImageBase64Utils.image2Base64Str("1.jpg");
        System.out.println(deEncodeByBase64(base64Str));
        System.out.println(deEncodeByPath("1.jpg"));
    }
}
