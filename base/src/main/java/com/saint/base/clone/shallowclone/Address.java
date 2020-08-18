package com.saint.base.clone.shallowclone;

/**
 * 局部变量Address类
 * 浅克隆，只克隆基本值类型，引用类型复制的只是指向原对象的内存地址的指针，两个对象修改一个另外一个也会改变。
 *
 * @author Saint
 * @createTime 2020-03-01 9:39
 */
public class Address {
    private String provices;
    private String city;

    public void setAddress(String provices, String city) {
        this.provices = provices;
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address [provices=" + provices + ", city=" + city + "]";
    }

}
