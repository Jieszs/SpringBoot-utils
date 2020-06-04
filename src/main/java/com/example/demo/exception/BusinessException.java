package com.example.demo.exception;

/**
 * 业务异常
 * Created by jcl on 2018/12/6
 */
public class BusinessException extends Exception {
    public BusinessException(String message) {
        super(message);
    }
}
