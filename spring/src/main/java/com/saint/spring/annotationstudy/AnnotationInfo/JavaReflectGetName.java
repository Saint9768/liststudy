package com.saint.spring.annotationstudy.AnnotationInfo;

import com.saint.spring.annotationstudy.annotation.TransactionalService;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-24 8:53
 */
@TransactionalService(name = "test")
public class JavaReflectGetName {
    public static void main(String[] args) {
        AnnotatedElement annotatedElement = JavaReflectGetName.class;
        // 从AnnotatedElement中获取指定注解
        TransactionalService annotation = annotatedElement.getAnnotation(TransactionalService.class);
        // 1. 显示调用属性方法TransactionalService#name()获取属性
//        String name = annotation.name();
//        System.out.println("TransactionalService.name() = " + name);

        // 2. 完全JAVA反射实现（ReflectionUtils为Spring反射工具类）
        // 返回所有无参方法的key/value，排除Annotation注解的方法
        ReflectionUtils.doWithMethods(TransactionalService.class,
                method -> System.out.printf("@TransactionalService.%s() = %s\n", method.getName(),
                        ReflectionUtils.invokeMethod(method, annotation))
                , method -> !method.getDeclaringClass().equals(Annotation.class));
    }
}
