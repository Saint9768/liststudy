package com.saint.spring;

import com.saint.spring.controller.UserController;
import com.saint.spring.service.UserService;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Saint
 * @createTime 2020-06-07 21:15
 */
public class MyTest {
    @Test
    public void test() throws Exception {
        UserController userController = new UserController();
        Class clazz = userController.getClass();
        UserService userService = new UserService();
        System.out.println(userService);
        Field serviceField = clazz.getDeclaredField("userService");
        serviceField.setAccessible(true);
        String name = serviceField.getName();
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        String methodName = "set" + name;
        Method method = clazz.getMethod(methodName, UserService.class);
        method.invoke(userController, userService);
        System.out.println(userController.getUserService());
    }
}
