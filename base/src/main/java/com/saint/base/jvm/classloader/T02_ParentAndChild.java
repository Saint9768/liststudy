package com.saint.base.jvm.classloader;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-03-01 22:16
 */
public class T02_ParentAndChild {
    public static void main(String[] args) {
        System.out.println(T02_ParentAndChild.class.getClassLoader());
        System.out.println(T02_ParentAndChild.class.getClassLoader().getClass().getClassLoader());
        System.out.println(T02_ParentAndChild.class.getClassLoader().getParent());
        System.out.println(T02_ParentAndChild.class.getClassLoader().getParent().getParent());
    }
}
