package com.example.demo.exception;

/**
 * 资源未找到
 * Created by jcl on 2018/12/6
 */
public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
