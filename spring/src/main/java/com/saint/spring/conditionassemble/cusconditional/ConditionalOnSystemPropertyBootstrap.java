package com.saint.spring.conditionassemble.cusconditional;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-25 7:17
 */
public class ConditionalOnSystemPropertyBootstrap {
    public static void main(String[] args) {
//        System.setProperty("language", "Chinese");
        System.setProperty("language", "English");

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ConditionalMessageConfiguration.class);
        // 启动上下文
        context.refresh();
        String message = context.getBean("message", String.class);
        System.out.println("当前消息为：" + message);
    }
}
