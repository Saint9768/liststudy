package com.saint.base.locktandhread.thread.problems.monitorSize;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-04-14 22:23
 */
public class MonitorSizeCountDownLatch {
    volatile List lists = new ArrayList();

    public void add(Object o) {
        lists.add(o);
    }

    public int getSize() {
        return lists.size();
    }

    public static void main(String[] args) {
        MonitorSizeCountDownLatch monitorSize = new MonitorSizeCountDownLatch();
        CountDownLatch monitor = new CountDownLatch(1);
        CountDownLatch add = new CountDownLatch(1);
        new Thread(() -> {
            System.out.println("添加线程开始");
            for (int i = 0; i < 10; i++) {
                monitorSize.add(i);
                System.out.println("添加了" + i);
                if (monitorSize.getSize() == 5) {
                    monitor.countDown();

                    try {
                        add.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("添加线程结束");
        }, "t1").start();

        new Thread(() -> {
            System.out.println("监控线程开始");
            if (monitorSize.getSize() != 5) {
                try {
                    monitor.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("监控线程结束");
            add.countDown();
        }, "t1").start();

    }
}
