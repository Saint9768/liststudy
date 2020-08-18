package com.saint.base.clone.deepclone;

/**
 * 局部变量Address类
 * 深克隆，Address类要实现Cloneable接口
 *
 * @author Saint
 * @createTime 2020-03-01 9:39
 */
public class Address implements Cloneable {
    private String provices;
    private String city;

    public void setAddress(String provices, String city) {
        this.provices = provices;
        this.city = city;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Address [provices=" + provices + ", city=" + city + "]";
    }


}
