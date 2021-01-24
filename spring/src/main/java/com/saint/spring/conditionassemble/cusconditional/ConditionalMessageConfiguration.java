package com.saint.spring.conditionassemble.cusconditional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 条件消息配置
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-25 7:15
 */
@Configuration
public class ConditionalMessageConfiguration {
    @ConditionalOnSystemProperty(name = "language", value = "Chinese")
    @Bean("message")
    public String chinese() {
        return "你好，世界！";
    }

    @ConditionalOnSystemProperty(name = "language", value = "English")
    @Bean("message")
    public String english() {
        return "Hello, World";
    }
}
