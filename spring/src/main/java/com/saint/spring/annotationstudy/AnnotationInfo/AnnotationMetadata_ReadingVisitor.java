package com.saint.spring.annotationstudy.AnnotationInfo;

import com.saint.spring.annotationstudy.annotation.TransactionalService;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-24 9:45
 */
@TransactionalService(name = "test")
public class AnnotationMetadata_ReadingVisitor {
    public static void main(String[] args) {
        AnnotatedElement annotatedElement = AnnotationMetadata_ReadingVisitor.class;
        TransactionalService transactionalService = annotatedElement.getAnnotation(TransactionalService.class);
        // 获取TransactionalService的所有元注解
        Set<Annotation> metaAnnotations = getAllAnnotations(transactionalService);
        metaAnnotations.forEach(AnnotationMetadata_ReadingVisitor::printAnnotationAttribute);
    }

    private static Set<Annotation> getAllAnnotations(Annotation annotation) {
        Annotation[] metaAnnotations = annotation.annotationType().getAnnotations();
        if (ObjectUtils.isEmpty(metaAnnotations)) {
            return Collections.emptySet();
        }
        // 获取所有非Java标准元注解集合
        Set<Annotation> metaAnnotationsSet = Stream.of(metaAnnotations)
                // 排除java.lang.annotation包下的注解
                .filter(metaAnnotation -> !Target.class.getPackage().equals(metaAnnotation.annotationType().getPackage()))
                .collect(Collectors.toSet());

        // 递归查找元注解的元注解集合
        Set<Annotation> metaMetaAnnotationsSet = metaAnnotationsSet.stream()
                .map(AnnotationMetadata_ReadingVisitor::getAllAnnotations)
                .collect(HashSet::new, Set::addAll, Set::addAll);

        // 添加递归结果
        metaAnnotationsSet.addAll(metaMetaAnnotationsSet);
        return metaAnnotationsSet;
    }

    private static void printAnnotationAttribute(Annotation annotation) {

        Class<?> annotationType = annotation.annotationType();
        // 完全 Java 反射实现（ReflectionUtils 为 Spring 反射工具类）
        ReflectionUtils.doWithMethods(annotationType,
                method -> System.out.printf("@%s.%s() = %s\n", annotationType.getSimpleName(),
                        method.getName(), ReflectionUtils.invokeMethod(method, annotation))
//                , method -> method.getParameterCount() == 0); // 选择无参数方法
                , method -> !method.getDeclaringClass().equals(Annotation.class));// 选择非 Annotation 方法


    }
}
