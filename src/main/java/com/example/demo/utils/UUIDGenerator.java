package com.example.demo.utils;

import java.util.UUID;

/**
 * 32位 UUID生成器
 */
public class UUIDGenerator {
    public static String generate() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
