package com.saint.base.locktandhread.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {
    static private final Map<String, Object> map = new HashMap<>();
    static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    /**
     * 读锁
     */
    static Lock r = rwl.readLock();
    /**
     * 写锁
     */
    static Lock w = rwl.writeLock();

    /**
     * 写
     */
    static public Object put(String key, Object value) {
        try {
            w.lock();
            System.out.println("正在写入 key:" + key + ",value:" + value + "开始.....");
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Object oj = map.put(key, value);
            System.out.println("正在写入 key:" + key + ",value:" + value + "结束.....");
            System.out.println();
            return oj;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            w.unlock();
        }
        return value;
    }

    /**
     * 读
     */
    static public void get(String key) {
        try {
            r.lock();
            System.out.println("正在读取 key:" + key + "开始");
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Object value = map.get(key);
            System.out.println("正在读取 key:" + key + ",value:" + value + "结束.....");
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            r.unlock();
        }
    }

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                ReadWriteLockTest.put(i + "", i + "");
            }
        }, "t1").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                ReadWriteLockTest.get(i + "");
            }
        }, "t2").start();
    }
}