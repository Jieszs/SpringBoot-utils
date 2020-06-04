package com.example.demo.utils;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Slf4j
public class ImageBase64Utils {
    /**
     * 将base64字符串转成图片
     * TODO
     *
     * @param imgStr base64图片字符串
     * @param path   目标输出路径
     * @return
     */
    public static boolean base64Str2Image(String imgStr, String path) {
        if (imgStr == null)
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // 解密base64图片字符串
            byte[] b = decoder.decodeBuffer(imgStr);
            // 处理数据,把负的byte字节数据改为正的,作用未知
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            File tempFile = new File(path);
            //文件夹不存在则自动创建
            if (!tempFile.getParentFile().exists()) {
                tempFile.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(tempFile);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            log.error("将base64字符串转成图片失败：" + e.getMessage(), e.getStackTrace());
            return false;
        }
    }

    /* 图片转base64字符串
     * @param imgFile 图片路径
     * @return base64字符串格式的图片
     */
    public static String image2Base64Str(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];  //根据文件流字节大小初始化data字节数组大小
            inputStream.read(data);  //将流读入data
            inputStream.close();  //关闭流
        } catch (IOException e) {
            log.error("图片转base64字符串：" + e.getMessage(), e.getStackTrace());
        }
        //将图片数组加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    /**
     * @param buffer 照片
     * @return String base64码字符串
     **/
    public static String bufferedImage2Base64String(BufferedImage buffer) {
        BASE64Encoder encoder = new BASE64Encoder();
        String base64String = "";
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(buffer, "jpg", baos);  //第二个参数“jpg”不需要修改
            byte[] bytes = baos.toByteArray();
            base64String = encoder.encodeBuffer(bytes).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64String;
    }

    /**
     * @param base64String base64码字符串
     *
     **/
    public static BufferedImage Base64String2BufferedImage(String base64String) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] bytes = decoder.decodeBuffer(base64String);
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        BufferedImage buffer = ImageIO.read(bais);
        return buffer;
    }

    public static void main(String[] args) {
        String base64Str = image2Base64Str("1.jpg");
        System.out.println(base64Str);
        boolean b = base64Str2Image(base64Str, "D:/2.jpg");
        System.out.println(b);
    }
}
