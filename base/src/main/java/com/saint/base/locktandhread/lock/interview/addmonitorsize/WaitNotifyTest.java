package com.saint.base.locktandhread.lock.interview.addmonitorsize;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-02-03 21:48
 */
public class WaitNotifyTest {
    volatile List lists = new ArrayList();

    public void add(Object o) {
        lists.add(o);
    }

    public Integer getSize() {
        return lists.size();
    }

    public static void main(String[] args) {
        final Object lock = new Object();
        WaitNotifyTest c = new WaitNotifyTest();

        new Thread(() -> {
            System.out.println("监控线程开始");
            synchronized (lock) {
                if (c.getSize() != 5) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("监控线程结束");
                lock.notify();
            }
        }, "t2").start();


        new Thread(() -> {
            System.out.println("add线程开始");
            synchronized (lock) {
                for (int i = 0; i < 10; i++) {
                    c.add(new Object());
                    System.out.println("加入数据" + i);
                    if (c.getSize() == 5) {
                        lock.notify();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            System.out.println("add线程结束");
        }, "t1").start();
    }
}
