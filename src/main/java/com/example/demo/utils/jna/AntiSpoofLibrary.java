package com.example.demo.utils.jna;


import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * 活体检测SDK AntiSpoof
 * 调用本地dll/so
 * linux: /usr/lib/libRobotCreateModel.so
 * win:所有DLL放同级目录
 *
 * @author zj
 * @date 2020/5/7
 */
public interface AntiSpoofLibrary extends Library {
    MklIntelThreadLibrary mkl = MklIntelThreadLibrary.INSTANCE;
    AntiSpoofLibrary INSTANCE = Native.load("AntiSpoof", AntiSpoofLibrary.class);

    /**
     * 初始化接口，返回：true正确，false错误
     *
     * @return
     */
    boolean InitSpoof(String modelPath);

    /**
     * 活体检测接口 ，返回：true 活体，false：假体
     *
     * @return
     */
    boolean AntiSpoofBase64(String rgbBase, String irBase, boolean print);
}
