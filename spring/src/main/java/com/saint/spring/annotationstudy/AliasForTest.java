package com.saint.spring.annotationstudy;

import com.saint.spring.annotationstudy.annotation.AliasForAnnotation;
import org.junit.Test;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-24 16:59
 */
@AliasForAnnotation(value = "aa", alias = "bb")
public class AliasForTest {

    @Test
    public void testAliasFor() {
        AliasForAnnotation ann = AnnotationUtils.findAnnotation(getClass(), AliasForAnnotation.class);
        System.out.println(ann.alias());
        System.out.println(ann.value());
        // 因为value和alias不一样，所以会报错
    }
}
