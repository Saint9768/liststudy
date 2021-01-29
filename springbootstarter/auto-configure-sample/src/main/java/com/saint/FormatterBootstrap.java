package com.saint;

import com.saint.autoconfigure.Formatter;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-29 15:28
 */
@EnableAutoConfiguration
public class FormatterBootstrap {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(FormatterBootstrap.class)
                .web(WebApplicationType.NONE)
                .run(args);
        Map<String, Object> data = new HashMap<>();
        data.put("name", "Saint");
        Map<String, Formatter> beansOfType = context.getBeansOfType(Formatter.class);
        // Bean不存在则抛出异常
        if (beansOfType.isEmpty()) {
            throw new NoSuchBeanDefinitionException(Formatter.class);
        }
        beansOfType.forEach((beanName, formatter) -> {
            System.out.printf("[Bean name : %s] %s,format(data):%s \n", beanName, formatter.getClass().getSimpleName(),
                    formatter.format(data));
        });
        context.close();
//        ConfigurableApplicationContext context = new SpringApplicationBuilder(FormatterBootstrap.class)
//                .web(WebApplicationType.NONE)
//                .run(args);
//        Map<String, Object> data = new HashMap<>();
//        data.put("name", "Saint");
//        Formatter bean = context.getBean(Formatter.class);
//        System.out.printf("%s,format(data):%s \n", bean.getClass().getSimpleName(), bean.format(data));
//        context.close();

    }
}
