package com.saint.mybatis;


import java.util.ArrayList;
import java.util.List;

interface Interceptor {
    Object plugin(Object target);
}

class InterceptorA implements Interceptor {
    @Override
    public Object plugin(Object target) {
        System.out.println("InterceptorA");
        return target;
    }
}

class InterceptorB implements Interceptor {
    @Override
    public Object plugin(Object target) {
        System.out.println("InterceptorB");
        return target;
    }
}

class InterceptorC implements Interceptor {
    @Override
    public Object plugin(Object target) {
        System.out.println("InterceptorC");
        return target;
    }
}

class InterceptorChain {
    private final List<Interceptor> interceptorList = new ArrayList<>();

    public void addInterceptor(Interceptor interceptor) {
        interceptorList.add(interceptor);
    }

    public Object pluginAll(Object target) {
        for (Interceptor interceptor : interceptorList) {
            target = interceptor.plugin(target);
        }
        return target;
    }
}

/**
 * 责任链模式
 *
 * @author Saint
 * @version 1.0
 * @createTime 2020-09-21 22:45
 */
public class InterceptDemo {
    public static void main(String[] args) {
        InterceptorA interceptorA = new InterceptorA();
        InterceptorB interceptorB = new InterceptorB();
        InterceptorC interceptorC = new InterceptorC();
        InterceptorChain interceptorChain = new InterceptorChain();
        interceptorChain.addInterceptor(interceptorA);
        interceptorChain.addInterceptor(interceptorB);
        interceptorChain.addInterceptor(interceptorC);
        interceptorChain.pluginAll(new Object());
    }
}