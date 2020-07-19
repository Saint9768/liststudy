package com.saint.base.agent.dynamicagent;

import java.lang.reflect.Proxy;

/**
 * @author Saint
 * @createTime 2020-07-20 7:40
 */
public class Main {
    public static void main(String[] args) {
        //创建中介类实例
        MyDynamicProxy inter = new MyDynamicProxy(new Vendor());

        //加上这句将会产生一个$Proxy().class文件，这个文件即为动态生成的代理类文件。
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        //调用Proxy类的newProxyInstance方法来获取一个代理类实例。
        // 这个代理类实现了我们指定的接口并且会把方法调用分发到指定的调用处理器。
        //获取代理类实例sell
        Sell sell = (Sell) Proxy.newProxyInstance(Sell.class.getClassLoader(), new Class[]{Sell.class}, inter);

        //通过代理类对象调用代理类方法，实际上会转到invoke方法调用。

        sell.sell();

        sell.add();
    }
}
