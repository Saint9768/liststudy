package com.saint.base.clone.serializedclone;

/**
 * 深克隆，引用类型和值类型都会单独克隆，一个引用类型值的改变不影响另外一个
 *
 * @author Saint
 * @createTime 2020-03-01 9:39
 */
public class Test {

    public static void main(String[] args) throws CloneNotSupportedException {
        Person p1 = new Person("zhangsan", 21);
        p1.setAddress("湖北省", "武汉市");
        Person p2 = CloneUtils.clone(p1);
        //两个person对象的内存地址不一样
        System.out.println("p1:" + p1);
        System.out.println("p1.getPname:" + p1.getPname().hashCode());

        System.out.println("p2:" + p2);
        System.out.println("p2.getPname:" + p2.getPname().hashCode());

        p1.display("p1");
        p2.display("p2");
        //引用类型修改一个，另外一个也会改变
        p2.setAddress("湖北省", "荆州市");
        System.out.println("将复制之后的对象地址修改：");
        p1.display("p1");
        p2.display("p2");
    }
}
