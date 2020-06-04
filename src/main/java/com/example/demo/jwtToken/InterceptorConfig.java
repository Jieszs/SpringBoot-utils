package com.example.demo.jwtToken;

import com.example.demo.constant.Namespace;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author zj
 * @date 2020/1/14
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    private static final String ROOT = "/" + Namespace.API;

    @Resource
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns(ROOT + "/**")//拦截ROOT下的所有请求
                .excludePathPatterns(ROOT + "/" + Namespace.BPM + "/**")
                .excludePathPatterns(ROOT + "/" + Namespace.MEETING_ROOM + "/**")
                .excludePathPatterns(ROOT + "/" + Namespace.FIELD_CONFIG + "/**")
                .excludePathPatterns(ROOT + "/" + Namespace.DICTIONARY_ITEM + "/**")
                .excludePathPatterns(ROOT + "/" + Namespace.DICTIONARY_TYPE + "/**")
                .excludePathPatterns(ROOT + "/" + Namespace.MEETING_BOOK + "/conflict")
                .excludePathPatterns(ROOT + "/" + Namespace.MEETING_BOOK + "/form");
    }

}
