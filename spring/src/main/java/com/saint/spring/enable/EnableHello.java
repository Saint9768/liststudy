package com.saint.spring.enable;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-24 17:25
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(HelloConfiguration.class)
public @interface EnableHello {
}
