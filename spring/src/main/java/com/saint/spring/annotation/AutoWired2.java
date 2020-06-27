package com.saint.spring.annotation;

import java.lang.annotation.*;

/**
 * 运行时生效，放在属性上，可以在文档中显示，可以被继承。
 * @author Saint
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
@Inherited
public @interface AutoWired2 {
}
