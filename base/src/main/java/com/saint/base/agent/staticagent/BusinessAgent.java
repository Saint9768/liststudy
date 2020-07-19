package com.saint.base.agent.staticagent;

/**
 * 微商代理
 *
 * @author Saint
 * @createTime 2020-07-20 7:18
 */
public class BusinessAgent implements Sell {
    /**
     * 聚合被代理类
     */
    private Vendor vendor;

    public BusinessAgent(Vendor vendor) {
        this.vendor = vendor;
    }

    @Override
    public void sell() {
        System.out.println("被代理了");
        vendor.sell();
    }

    @Override
    public void add() {
        vendor.add();
    }
}
