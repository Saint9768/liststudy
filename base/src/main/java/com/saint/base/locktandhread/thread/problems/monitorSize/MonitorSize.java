package com.saint.base.locktandhread.thread.problems.monitorSize;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-04-14 22:21
 */
public class MonitorSize {
    volatile List lists = new ArrayList();

    public void add(Object o) {
        lists.add(o);
    }

    public int getSize() {
        return lists.size();
    }

    public static void main(String[] args) {
        MonitorSize monitorSize = new MonitorSize();
        final Object lock = new Object();
        new Thread(() -> {
            System.out.println("添加线程开始");
            synchronized (lock) {
                for (int i = 0; i < 10; i++) {
                    monitorSize.add(i);
                    System.out.println("添加了" + i);
                    if (monitorSize.getSize() == 5) {
                        // 唤醒t2
                        lock.notify();

                        try {
                            // 阻塞t1
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                System.out.println("添加线程结束");
            }
        }, "t1").start();

        new Thread(() -> {
            System.out.println("监控线程开始");
            synchronized (lock) {
                if (monitorSize.getSize() != 5) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("监控线程结束");
                lock.notify();
            }
        }, "t1").start();

    }
}
