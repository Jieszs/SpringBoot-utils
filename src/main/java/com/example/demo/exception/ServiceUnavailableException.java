package com.example.demo.exception;

/**
 * 请求第三方接口失败异常
 * Created by jcl on 2018/12/6
 */
public class ServiceUnavailableException extends Exception {
    public ServiceUnavailableException(String message) {
        super(message);
    }
}
