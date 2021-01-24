package com.saint.spring.enable;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-24 17:24
 */
@Configuration
public class HelloConfiguration {

    @Bean
    public String helloWorld() {
        System.out.println("初始化Hello World");
        return "Hello ,World!";
    }
}
