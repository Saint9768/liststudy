package com.saint.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Saint
 * @version 1.0
 * @ConditionalOnResource 表示当指定资源文件存在时才加载该Bean
 * @ConditionalOnExpression Spring表达式条件注解 多个之间使用&&
 * @createTime 2021-01-29 14:47
 */
@Configuration
@ConditionalOnProperty(prefix = "formatter", name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnResource(resources = "META-INF/spring.factories")
@ConditionalOnExpression("${formatter.enabled:true} && ${formatter.enabled.dup:true}")
public class ForMatterAutoConfiguration {

    /**
     * 存在两个Formatter Bean是冲突的，所以要排除一个
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingClass(value = "com.fasterxml.jackson.databind.ObjectMapper")
    public Formatter defaultFormatter() {
        return new DefaultFormatter();
    }


    /**
     * 当objectMapper的class存在，但当前上下文中没有Bean时，构造
     *
     * @return
     */
    @Bean
    @ConditionalOnClass(name = "com.fasterxml.jackson.databind.ObjectMapper")
    @ConditionalOnMissingBean(type = "com.fasterxml.jackson.databind.ObjectMapper")
    public Formatter jsonFormatter() {
        return new JsonFormatter();
    }

    @Bean
    @ConditionalOnBean(ObjectMapper.class)
    public Formatter objectMapperFormatter(ObjectMapper objectMapper) {
        return new JsonFormatter(objectMapper);
    }

    //    @Bean
//    @ConditionalOnClass(name = "com.fasterxml.jackson.databind.ObjectMapper")
//    public Formatter jsonFormatter() {
//        return new JsonFormatter();
//    }
}
