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
    public void test() throws Exception {
        UserController userController = new UserController();
        Class<? extends UserController> clazz = userController.getClass();
        Stream.of(clazz.getDeclaredFields()).forEach((field) -> {
            AutoWired2 annotation = field.getAnnotation(AutoWired2.class);
            if (annotation != null) {
                field.setAccessible(true);
                Class type = field.getType();

                try {
                    Object o = type.newInstance();
                    field.set(userController, o);
                } catch (InstantiationException var5) {
                    var5.printStackTrace();
                } catch (IllegalAccessException var6) {
                    var6.printStackTrace();
                }
            }

        });
        System.out.println(userController.getUserService());
    }
}
