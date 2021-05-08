package com.saint.base.jvm.classloader;

/**
 * 类重复加载
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-03-01 22:35
 */
public class T06_ClassReloading {
    public static void main(String[] args) throws ClassNotFoundException {
        T04_CustomClassLoader classLoader = new T04_CustomClassLoader();
        Class clazz = classLoader.loadClass("com.mashibing.jvm.Hello");

        classLoader = null;
        System.out.println(clazz.hashCode());

        classLoader = null;

        classLoader = new T04_CustomClassLoader();
        Class clazz1 = classLoader.loadClass("com.mashibing.jvm.Hello");
        System.out.println(clazz1.hashCode());

        System.out.println(clazz == clazz1);
    }
}
