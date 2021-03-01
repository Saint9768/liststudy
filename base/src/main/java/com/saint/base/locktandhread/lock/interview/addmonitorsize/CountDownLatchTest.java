package com.saint.base.locktandhread.lock.interview.addmonitorsize;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 线程1往容器中添加10个元素
 * 线程2实时监控元素个数，当个数到5个时，线程2给出提示并退出。
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-02-03 7:52
 */
public class CountDownLatchTest {
    volatile List lists = new ArrayList();

    public void add(Object o) {
        lists.add(o);
    }

    public Integer getSize() {
        return lists.size();
    }

    public static void main(String[] args) {
        CountDownLatchTest c = new CountDownLatchTest();
        // 一个门栓
        CountDownLatch monitorLatch = new CountDownLatch(1);
        CountDownLatch producerLatch = new CountDownLatch(1);

        new Thread(() -> {
            System.out.println("添加线程开始!");
            for (int i = 0; i < 10; i++) {
                c.add(new Object());
                System.out.println("添加了" + i);
                if (c.getSize() == 5) {
                    monitorLatch.countDown();
                    try {
                        producerLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("添加线程结束！");
        }, "t1").start();

        new Thread(() -> {
            System.out.println("监控开始!");
            while (c.getSize() != 5) {
                try {
                    monitorLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("监控结束!");
            producerLatch.countDown();

        }, "t1").start();
    }
}
