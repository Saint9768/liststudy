package com.saint.base.agent.dynamicagent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * JDK动态代理：中介(中间)类
 *
 * @author Saint
 * @createTime 2020-07-20 7:35
 */
public class MyDynamicProxy implements InvocationHandler {

    /**
     * obj为委托类对象
     */
    private Object obj;

    public MyDynamicProxy(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before");

        Object result = method.invoke(obj, args);

        System.out.println("after");

        return result;
    }
}
