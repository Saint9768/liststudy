package com.saint.mybatis;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义自旋锁
 *
 * @author Saint
 * @version 1.0
 * @createTime 2020-09-20 22:08
 */
public class OneSelfLock {

    private static final Unsafe unsafe;
    private static final long valueOffset;

    static {
        try {
            Class<Unsafe> unsafeClass = Unsafe.class;
            Field theUnSafe = unsafeClass.getDeclaredField("theUnsafe");
            theUnSafe.setAccessible(true);
            unsafe = (Unsafe) theUnSafe.get(null);
            valueOffset = unsafe.objectFieldOffset
                    (AtomicInteger.class.getDeclaredField("value"));
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

    private volatile int value;

    public void lock() {
        for (; ; ) {
            if (unsafe.compareAndSwapInt(this, valueOffset, 0, 1)) {
                return;
            }
            //线程让步
            Thread.yield();
        }
    }

    public void unlock() {
        value = 0;
    }

}
