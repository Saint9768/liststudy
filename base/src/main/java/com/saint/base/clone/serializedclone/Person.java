package com.saint.base.clone.serializedclone;

import java.io.Serializable;

/**
 * Person类，要被克隆的类
 * 利用序列化实现深克隆
 *
 * @author Saint
 * @createTime 2020-03-01 9:38
 */
public class Person implements Serializable {
    public String pname;
    public int page;
    public Address address;

    public Person() {
    }

    public Person(String pname, int page) {
        this.pname = pname;
        this.page = page;
        this.address = new Address();
    }

    public void setAddress(String provices, String city) {
        address.setAddress(provices, city);
    }

    public void display(String name) {
        System.out.println(name + ":" + "pname=" + pname + ", page=" + page + "," + address);
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
