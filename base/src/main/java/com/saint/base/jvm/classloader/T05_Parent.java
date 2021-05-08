package com.saint.base.jvm.classloader;

/**
 * 指定类加载器的父类加载器
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-03-01 22:25
 */
public class T05_Parent {
    private static final T04_CustomClassLoader parent = new T04_CustomClassLoader();

    private static class MyLoader extends ClassLoader {
        public MyLoader() {
            super(parent);
        }
    }
}
