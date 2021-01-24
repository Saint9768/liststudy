package com.saint.spring.autoassemble;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Spring web  mvc配置类
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-24 21:56
 */
@EnableWebMvc
@Configuration
@ComponentScan(basePackageClasses = SpringWebMvcConfiguration.class)
public class SpringWebMvcConfiguration {

}
