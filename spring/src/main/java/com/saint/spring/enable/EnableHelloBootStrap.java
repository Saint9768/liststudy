package com.saint.spring.enable;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-24 17:30
 */
@Configuration
@EnableHello
public class EnableHelloBootStrap {
    public static void main(String[] args) {
        // 构建Annotation配置驱动Spring上下文
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 注册当前引导类到Spring上下文
        context.register(EnableHelloBootStrap.class);
        // 启动上下文
        context.refresh();
        // 获取名称为“helloWorld”的Bean对象
        String helloWorld = context.getBean("helloWorld", String.class);
        System.out.println(helloWorld);
        context.close();
    }
}
