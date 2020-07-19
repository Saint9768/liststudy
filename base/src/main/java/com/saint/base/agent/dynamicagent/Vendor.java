package com.saint.base.agent.dynamicagent;

/**
 * Vendor类代表生产厂家
 *
 * @author Saint
 * @createTime 2020-07-20 7:14
 */
public class Vendor implements Sell {
    @Override
    public void sell() {
        System.out.println("In sell method");
    }

    @Override
    public void add() {
        System.out.println("Add method");
    }
}
