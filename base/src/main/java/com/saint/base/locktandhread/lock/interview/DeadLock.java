package com.saint.base.locktandhread.lock.interview;

import java.util.concurrent.TimeUnit;

/**
 * 线程死锁
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-02-02 9:08
 */
public class DeadLock {
    private static Object o1, o2;

    public static void main(String[] args) {
        o1 = new Object();
        o2 = new Object();

        new Thread(() -> {
            System.out.println("线程" + Thread.currentThread().getName() + "开始执行");
            synchronized (o1) {
                System.out.println("线程：" + Thread.currentThread().getName() + "获取o1锁");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o2) {
                    System.out.println("线程：" + Thread.currentThread().getName() + "获取o2锁");
                }
            }
        }, "t1").start();

        new Thread(() -> {
            System.out.println("线程" + Thread.currentThread().getName() + "开始执行");
            synchronized (o2) {
                System.out.println("线程：" + Thread.currentThread().getName() + "获取o2锁");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o1) {
                    System.out.println("线程：" + Thread.currentThread().getName() + "获取o1锁");
                }
            }
        }, "t2").start();
    }
}
