package com.example.demo;

import com.example.demo.utils.EmailUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class DemoApplicationTests {
    @Resource
    EmailUtils emailUtils;

    @Test
    void contextLoads() {
        String templateName = "resetPassword";
        Map<String, Object> variables = new HashMap<>();
        variables.put("password", "123456");
        variables.put("agentName", "卢本伟");
        emailUtils.sendTemplate("601116847@qq.com", "【智能客服系统】重置密码", templateName, variables);
    }

}
