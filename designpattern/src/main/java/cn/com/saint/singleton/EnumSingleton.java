package cn.com.saint.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 枚举单例
 * 优点：简单，避免反射、序列化问题。
 * 缺点：可读性不高。开发中很少用到，也不建议用。
 * 默认枚举单例是线程安全的，并且在任何情况下都是单例的。
 * 之前的几种单例模式，有一种情况他们会创建对象，那就反序列化。
 * 将一个单例实例对象写到磁盘再读回来，从而获得了一个实例。反序列化操作提供了readResolve方法
 * 这个方法可以让开发人员控制对象的反序列化。
 *
 * @author Saint
 * @createTime 2020-02-27 19:20
 */
public enum EnumSingleton {
    INSTANCE;

    public EnumSingleton getInstance() {
        return INSTANCE;
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        EnumSingleton singleton1 = EnumSingleton.INSTANCE;
        EnumSingleton singleton2 = EnumSingleton.INSTANCE;
        System.out.println("正常情况下，实例化两个实例是否相同：" + (singleton1 == singleton2));
        Constructor<EnumSingleton> constructor = null;
        constructor = EnumSingleton.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        EnumSingleton singleton3 = null;
        //因为自身的类没有无参构造方法导致异常
        singleton3 = constructor.newInstance();
        System.out.println(singleton1 + "\n" + singleton2 + "\n" + singleton3);
        System.out.println("通过反射攻击单例模式情况下，实例化两个实例是否相同：" + (singleton1 == singleton3));
    }
}
