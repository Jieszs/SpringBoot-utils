package com.example.demo.utils;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

import java.awt.image.BufferedImage;

/**
 * 图片压缩
 * 依赖
 * <dependency>
 * <groupId>net.coobird</groupId>
 * <artifactId>thumbnailator</artifactId>
 * <version>0.4.11</version>
 * </dependency>
 */
@Slf4j
public class ResizeImageUtils {

    /**
     * @param base64Img  图片base64字符串
     * @param targetSize 目标base64长度
     * @return
     */
    public static String resizeImage(String base64Img, Integer targetSize) {
        try {
            BufferedImage src = ImageBase64Utils.Base64String2BufferedImage(base64Img);
            BufferedImage output = Thumbnails.of(src).size(480, 320).asBufferedImage();
            String base64 = ImageBase64Utils.bufferedImage2Base64String(output);
            while (base64.length() > targetSize) {
                output = Thumbnails.of(output).scale(0.9f).asBufferedImage();
                base64 = ImageBase64Utils.bufferedImage2Base64String(output);
            }
            return base64;
        } catch (Exception e) {
            return base64Img;
        }
    }

    public static void main(String[] args) {
        String base64Str = ImageBase64Utils.image2Base64Str("1.jpg");
        boolean b = ImageBase64Utils.base64Str2Image(resizeImage(base64Str, 10 * 1024), "D:/2.jpg");
        System.out.println(b);

    }
}
