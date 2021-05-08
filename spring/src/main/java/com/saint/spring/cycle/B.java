package com.saint.spring.cycle;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-03-23 21:40
 */
public class B {

    private A a;

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    @Override
    public String toString() {
        return "B{" +
                "a=" + a +
                '}';
    }
}
