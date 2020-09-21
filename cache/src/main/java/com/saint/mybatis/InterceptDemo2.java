package com.saint.mybatis;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

interface Interceptor1 {
    Object plugin(Object target, InterceptorChain1 chain);
}

class InterceptorA1 implements Interceptor1 {
    @Override
    public Object plugin(Object target, InterceptorChain1 chain) {
        System.out.println("InterceptorA");
        return chain.plugin(target);
    }
}

class InterceptorB1 implements Interceptor1 {
    @Override
    public Object plugin(Object target, InterceptorChain1 chain) {
        System.out.println("InterceptorB");
        return chain.plugin(target);
    }
}

class InterceptorC1 implements Interceptor1 {
    @Override
    public Object plugin(Object target, InterceptorChain1 chain) {
        System.out.println("InterceptorC");
        return target;
    }
}

class InterceptorChain1 {
    private final List<Interceptor1> interceptorList = new ArrayList<>();
    private Iterator<Interceptor1> iterator;

    public void addInterceptor(Interceptor1 interceptor) {
        interceptorList.add(interceptor);
    }

    public Object plugin(Object target) {
        if (iterator == null) {
            iterator = interceptorList.iterator();
        }
        if (iterator.hasNext()) {
            Interceptor1 next = iterator.next();
            next.plugin(target, this);
        }
        return target;
    }

    public Object pluginAll(Object target) {
        for (Interceptor1 interceptor : interceptorList) {
            target = interceptor.plugin(target, this);
        }
        return target;
    }
}

/**
 * 责任链模式2  拦截器
 *
 * @author Saint
 * @version 1.0
 * @createTime 2020-09-21 22:45
 */
public class InterceptDemo2 {
    public static void main(String[] args) {
        InterceptorA1 interceptorA = new InterceptorA1();
        InterceptorB1 interceptorB = new InterceptorB1();
        InterceptorC1 interceptorC = new InterceptorC1();
        InterceptorChain1 interceptorChain = new InterceptorChain1();
        interceptorChain.addInterceptor(interceptorA);
        interceptorChain.addInterceptor(interceptorB);
        interceptorChain.addInterceptor(interceptorC);
        interceptorChain.plugin(new Object());
    }
}
