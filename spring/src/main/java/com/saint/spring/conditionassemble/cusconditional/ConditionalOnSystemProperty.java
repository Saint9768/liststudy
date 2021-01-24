package com.saint.spring.conditionassemble.cusconditional;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * 系统属性名称与属性值匹配条件注解
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-25 7:07
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnSystemPropertyCondition.class)
public @interface ConditionalOnSystemProperty {
    String name();

    String value();
}
