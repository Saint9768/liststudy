package com.saint.spring.annotationstudy.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-24 16:58
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface AliasForAnnotation {
    @AliasFor("alias")
    String value() default "";

    @AliasFor("value")
    String alias() default "";
}
