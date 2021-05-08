package com.saint.spring.cycle;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-03-23 21:40
 */
public class A {
    private B b;

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "A{" +
                "b=" + b +
                '}';
    }
}
