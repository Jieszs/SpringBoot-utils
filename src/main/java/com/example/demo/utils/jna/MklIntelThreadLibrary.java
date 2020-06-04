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
public interface MklIntelThreadLibrary extends Library {
    MklIntelThreadLibrary INSTANCE = Native.load("mkl_intel_thread", MklIntelThreadLibrary.class);
}
