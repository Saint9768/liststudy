package com.saint.spring.annotationstudy.annotation;

import java.lang.annotation.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-23 21:46
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Transactional
@Documented
@Service
public @interface TransactionalService {

    /**
     * 服务Bean名称
     *
     * @return
     */
    String name() default "";
}
