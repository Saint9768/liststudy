package com.saint.spring.test;

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
        //获取类中的属性值
        Field serviceField = clazz.getDeclaredField("userService");
        //在访问的时候如果是私有的访问类型，也可以直接进行访问。
        serviceField.setAccessible(true);
        //获取属性的名称
        String name = serviceField.getName();
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        String methodName = "set" + name;
        //获取方法对象
        Method method = clazz.getMethod(methodName, UserService.class);
        //执行set方法
        method.invoke(userController, userService);
        System.out.println(userController.getUserService());
    }
}
