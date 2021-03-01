package com.saint.base.locktandhread.thread.problems.线程之间顺序输出;

import java.util.concurrent.CountDownLatch;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-02-21 18:58
 */
public class SynchronizedMethod {

    private static final CountDownLatch latch = new CountDownLatch(1);
    static Object o = new Object();

    public static void main(String[] args) {
        char[] aI = new char[]{'1', '2', '3', '4', '5'};
        char[] aC = new char[]{'a', 'b', 'c', 'd', 'e'};

        new Thread(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (o) {
                for (char c : aI) {
                    System.out.print(c + " ");
                    // 唤醒第二个线程
                    o.notify();
                    try {
                        // 释放锁
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //不管是线程t1还是线程t2总有一个线程是wait的,所有需要唤醒。
                o.notify();
            }
        }, "t1").start();

        new Thread(() -> {
            latch.countDown();
            synchronized (o) {
                for (char c : aC) {
                    System.out.print(c + " ");
                    // 唤醒第二个线程
                    o.notify();
                    try {
                        // 释放锁
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //不管是线程t1还是线程t2总有一个线程是wait的,所有需要唤醒。
                o.notify();
            }
        }, "t2").start();
    }
}
