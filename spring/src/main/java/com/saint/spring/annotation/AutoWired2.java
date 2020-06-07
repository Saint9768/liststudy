package com.saint.spring.annotation;

import java.lang.annotation.*;

/**
 * @author Saint
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
@Inherited
public @interface AutoWired2 {
}
