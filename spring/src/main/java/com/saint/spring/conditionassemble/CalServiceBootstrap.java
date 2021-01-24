package com.saint.spring.conditionassemble;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.AbstractEnvironment;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-24 22:16
 */
@Configuration
@ComponentScan(basePackageClasses = CalculateService.class)
public class CalServiceBootstrap {
    static {
        // 设置spring profile
//        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "Java8");
        //
        System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, "Java7");
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(CalServiceBootstrap.class);
        context.refresh();
        CalculateService bean = context.getBean(CalculateService.class);
        bean.sum(1, 2, 3, 4, 5);
        context.close();
    }
}
