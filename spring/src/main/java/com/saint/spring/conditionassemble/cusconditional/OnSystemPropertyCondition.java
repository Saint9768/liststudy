package com.saint.spring.conditionassemble.cusconditional;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;

import java.util.Objects;

/**
 * 系统属性值与值匹配条件
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-25 7:09
 */
public class OnSystemPropertyCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        MultiValueMap<String, Object> attributes =
                metadata.getAllAnnotationAttributes(ConditionalOnSystemProperty.class.getName());
        String name = (String) attributes.getFirst("name");
        String value = (String) attributes.getFirst("value");
        String systemPropertyValue = System.getProperty(name);
        // 比较系统属性值和方法的值
        if (Objects.equals(systemPropertyValue, value)) {
            System.out.printf("系统属性【名称： %s】找到匹配值：%s \n", name, value);
            return true;
        }
        return false;
    }
}
