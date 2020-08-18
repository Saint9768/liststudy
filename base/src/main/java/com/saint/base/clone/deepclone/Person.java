package com.saint.base.clone.deepclone;

/**
 * Person类，要被克隆的类
 *
 * @author Saint
 * @createTime 2020-03-01 9:38
 */
public class Person implements Cloneable {
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

    /**
     * 深克隆，这里要对address进行单独克隆
     *
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Person p = (Person) super.clone();
        p.address = (Address) address.clone();
        return p;
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
