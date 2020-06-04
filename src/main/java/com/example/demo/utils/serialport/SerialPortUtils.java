package com.example.demo.utils.serialport;


import com.example.demo.exception.BusinessException;
import gnu.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

import static com.example.demo.utils.HexStringUtils.bytesToHexFun2;
import static com.example.demo.utils.HexStringUtils.hexString2Bytes;

/**
 *
 * 串口工具类
 *
 *  1.依赖
 * <dependency>
 * <groupId>org.bidib.jbidib.org.qbang.rxtx</groupId>
 * artifactId>rxtxcomm</artifactId>
 * <version>2.2</version>
 * </dependency>
 * 2. rxtxParallel.dll rxtxSerial.dll 放入jre/bin目录下
 */
public class SerialPortUtils {
    private static final Logger logger = LoggerFactory.getLogger(SerialPortUtils.class);//slf4j 日志记录器

    public static SerialPort realSerialPort = null;
    public static Long QRCODE_TIME = null;
    public static String qrCode = null;

    /**
     * 查找电脑上所有可用 com 端口
     *
     * @return 可用端口名称列表，没有时 列表为空
     */
    public static final ArrayList<String> findSystemAllComPort() {
        /**
         *  getPortIdentifiers：获得电脑主板当前所有可用串口
         */
        Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
        ArrayList<String> portNameList = new ArrayList<>();

        /**
         *  将可用串口名添加到 List 列表
         */
        while (portList.hasMoreElements()) {
            String portName = portList.nextElement().getName();//名称如 COM1、COM2....
            portNameList.add(portName);
        }
        return portNameList;
    }

    /**
     * 打开电脑上指定的串口
     *
     * @param portName 端口名称，如 COM1，为 null 时，默认使用电脑中能用的端口中的第一个
     * @param b        波特率(baudrate)，如 9600
     * @param d        数据位（datebits），如 SerialPort.DATABITS_8 = 8
     * @param s        停止位（stopbits），如 SerialPort.STOPBITS_1 = 1
     * @param p        校验位 (parity)，如 SerialPort.PARITY_NONE = 0
     * @return 打开的串口对象，打开失败时，返回 null
     */
    public static final SerialPort openComPort(String portName, int b, int d, int s, int p) {
        CommPort commPort = null;
        try {
            //当没有传入可用的 com 口时，默认使用电脑中可用的 com 口中的第一个
            if (StringUtils.isEmpty(portName)) {
                List<String> comPortList = findSystemAllComPort();
                if (comPortList != null && comPortList.size() > 0) {
                    portName = comPortList.get(0);
                }
            }
            logger.info("开始打开串口：portName=" + portName + ",baudrate=" + b + ",datebits=" + d + ",stopbits=" + s + ",parity=" + p);

            //通过端口名称识别指定 COM 端口
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
            /**
             * open(String TheOwner, int i)：打开端口
             * TheOwner 自定义一个端口名称，随便自定义即可
             * i：打开的端口的超时时间，单位毫秒，超时则抛出异常：PortInUseException if in use.
             * 如果此时串口已经被占用，则抛出异常：gnu.io.PortInUseException: Unknown Application
             */
            commPort = portIdentifier.open(portName, 5000);
            /**
             * 判断端口是不是串口
             * public abstract class SerialPort extends CommPort
             */
            if (commPort instanceof SerialPort) {
                SerialPort serialPort = (SerialPort) commPort;
                /**
                 * 设置串口参数：setSerialPortParams( int b, int d, int s, int p )
                 * b：波特率（baudrate）
                 * d：数据位（datebits），SerialPort 支持 5,6,7,8
                 * s：停止位（stopbits），SerialPort 支持 1,2,3
                 * p：校验位 (parity)，SerialPort 支持 0,1,2,3,4
                 * 如果参数设置错误，则抛出异常：gnu.io.UnsupportedCommOperationException: Invalid Parameter
                 * 此时必须关闭串口，否则下次 portIdentifier.open 时会打不开串口，因为已经被占用
                 */
                serialPort.setSerialPortParams(b, d, s, p);
                logger.info("打开串口 " + portName + " 成功...");
                return serialPort;
            } else {
                logger.error("当前端口 " + commPort.getName() + " 不是串口...");
            }
        } catch (NoSuchPortException e) {
            e.printStackTrace();
        } catch (PortInUseException e) {
            logger.warn("串口 " + portName + " 已经被占用，请先解除占用...");
            e.printStackTrace();
        } catch (UnsupportedCommOperationException e) {
            logger.warn("串口参数设置错误，关闭串口，数据位[5-8]、停止位[1-3]、验证位[0-4]...");
            e.printStackTrace();
            if (commPort != null) {//此时必须关闭串口，否则下次 portIdentifier.open 时会打不开串口，因为已经被占用
                commPort.close();
            }
        }
        logger.error("打开串口 " + portName + " 失败...");
        return null;
    }

    /**
     * 往串口发送数据
     *
     * @param serialPort 串口对象
     * @param orders     待发送数据
     */
    public static void sendDataToComPort(SerialPort serialPort, byte[] orders) {
        OutputStream outputStream = null;
        try {
            if (serialPort != null) {
                outputStream = serialPort.getOutputStream();
                outputStream.write(orders);
                outputStream.flush();
                logger.info("往串口 " + serialPort.getName() + " 发送数据：" + Arrays.toString(orders) + " 完成...");
            } else {
                logger.error("gnu.io.SerialPort 为null，取消数据发送...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 从串口读取数据
     *
     * @param serialPort 要读取的串口
     * @return 读取的数据
     */
    public static byte[] readData(SerialPort serialPort) {
        InputStream is = null;
        byte[] bytes = null;
        try {
            is = serialPort.getInputStream();//获得串口的输入流
            int bufflenth = is.available();//获得数据长度
            while (bufflenth != 0) {
                bytes = new byte[bufflenth];//初始化byte数组
                is.read(bytes);
                bufflenth = is.available();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    /**
     * 给串口设置监听
     *
     * @param serialPort
     * @param listener
     */
    public static void setListenerToSerialPort(SerialPort serialPort, SerialPortEventListener listener) {
        try {
            //给串口添加事件监听
            serialPort.addEventListener(listener);
        } catch (TooManyListenersException e) {
            e.printStackTrace();
        }
        serialPort.notifyOnDataAvailable(true);//串口有数据监听
        serialPort.notifyOnBreakInterrupt(true);//中断事件监听
    }

    /**
     * 关闭串口
     *
     * @param serialPort 待关闭的串口对象
     */
    public static void closeComPort(SerialPort serialPort) {
        if (serialPort != null) {
            serialPort.close();
            logger.info("关闭串口 " + serialPort.getName());
        }
    }



    /**
     * 初始化找到二维码模块的串口并开启在线模式
     *
     * @return
     * @throws InterruptedException
     */
    public static void init() throws InterruptedException {
        //发起在线模式指令
        String order = "50 31 31 31 30 3B";
        byte[] bytes = hexString2Bytes(order);
        ArrayList<String> allComPort = findSystemAllComPort();
        if (CollectionUtils.isEmpty(allComPort)) {
            return;
        }
        //遍历找到所需要的串口
        for (String portName : allComPort) {
            SerialPort serialPort = SerialPortUtils.openComPort(portName, 115200, 8, 1, 0);
            SerialPortUtils.sendDataToComPort(serialPort, bytes);
            byte[] readDataBytes = null;
            for (int i = 0; i < 20; i++) {
                readDataBytes = SerialPortUtils.readData(serialPort);
                if (readDataBytes != null) break;
                Thread.sleep(500);
            }
            if (readDataBytes != null) {
                //找到结束
                String readData = new String(readDataBytes);
                if (readData.contains("P1110ok")) {
                    realSerialPort = serialPort;
                    break;
                }
            }
            SerialPortUtils.closeComPort(serialPort);
        }
        //设置串口的listener
        SerialPortUtils.setListenerToSerialPort(realSerialPort, new SerialPortEventListener() {
            @Override
            public void serialEvent(SerialPortEvent arg0) {
                if (arg0.getEventType() == SerialPortEvent.DATA_AVAILABLE) {//数据通知
                    byte[] readDataBytes = SerialPortUtils.readData(realSerialPort);
                    QRCODE_TIME = System.currentTimeMillis() / 1000;
                    //解析二维码模组返回的数据
                    String hexFun = bytesToHexFun2(readDataBytes);
                    if (hexFun.length() > 400) {
                        String left = hexFun.substring(0, 486);
                        String right = hexFun.substring(488, hexFun.length() - 2);
                        String code = left + right;
                        BASE64Encoder base64Encoder = new BASE64Encoder();
                        qrCode = base64Encoder.encode(hexString2Bytes(code)).replaceAll("\r\n", "");
                    }
                }
            }
        });
    }

    /**
     * 获取二维码
     *
     * @return
     * @throws InterruptedException
     * @throws BusinessException
     * @throws IOException
     */
    public static String getQrCode() throws InterruptedException, BusinessException, IOException {
        Long now = System.currentTimeMillis() / 1000;
        for (int i = 0; i < 60; i++) {
            if (QRCODE_TIME != null && QRCODE_TIME > now) {
                break;
            }
            Thread.sleep(500);
        }
        if (QRCODE_TIME == null || QRCODE_TIME < now) {
            throw new BusinessException("没有扫描到二维码");
        }
        return qrCode;
    }

    public static void main(String[] args) throws IOException, InterruptedException, BusinessException {
        init();
        getQrCode();
    }
}
