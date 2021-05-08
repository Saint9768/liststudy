package com.saint.base.jvm.classloader;

/**
 * 手动加载类
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-03-01 22:18
 */
public class T03_LoadClassByHand {
    static {
        System.out.println("Hello ClassLoader");
    }

    public static void main(String[] args) {
        try {
            Class clazz = T03_LoadClassByHand.class.getClassLoader().loadClass("com.saint.base.jvm.classloader" +
                    ".T02_ParentAndChild");
            System.out.println(clazz.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
