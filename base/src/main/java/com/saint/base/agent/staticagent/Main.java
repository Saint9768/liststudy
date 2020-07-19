package com.saint.base.agent.staticagent;

/**
 * @author Saint
 * @createTime 2020-07-20 7:20
 */
public class Main {
    public static void main(String[] args) {
        //创建被代理类
        Vendor vendor = new Vendor();
        //创建代理类
        BusinessAgent businessAgent = new BusinessAgent(vendor);
        //调用被代理类的add方法。
        businessAgent.add();
        businessAgent.sell();
    }
}
