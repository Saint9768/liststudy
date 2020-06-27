package com.saint.spring;

import com.saint.spring.annotation.AutoWired2;
import com.saint.spring.controller.UserController;
import org.junit.Test;

import java.util.stream.Stream;

/**
 * @author Saint
 * @createTime 2020-06-07 21:16
 */
public class MyTest2 {

    @Test
    public void test() {
        UserController userController = new UserController();
        Class<? extends UserController> clazz = userController.getClass();
        Stream.of(clazz.getDeclaredFields()).forEach((field) -> {
            //当前属性是否添加了@Autowired2注解
            AutoWired2 annotation = field.getAnnotation(AutoWired2.class);
            if (annotation != null) {
                field.setAccessible(true);
                //获取当前类型（使用了注解的类型）
                Class type = field.getType();

                try {
                    Object o = type.newInstance();
                    //注入属性
                    field.set(userController, o);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        });
        System.out.println(userController.getUserService());
    }
}
