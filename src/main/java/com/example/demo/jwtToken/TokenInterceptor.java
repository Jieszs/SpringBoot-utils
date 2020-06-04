package com.example.demo.jwtToken;


import com.example.demo.constant.HttpHeader;
import com.example.demo.exception.UnauthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zj
 * @date 2020/1/14
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        //允许get查询方法
        String token = request.getHeader(HttpHeader.AUTHORIZATION);
        if (StringUtils.isEmpty(token)) {
            throw new UnauthorizedException("token为空");
        }
        int code = TokenUtils.verify(token);
        if (code == -1) {
            throw new UnauthorizedException("token校验不通过");
        }
        if (code == 0) {
            throw new UnauthorizedException("token过期");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

