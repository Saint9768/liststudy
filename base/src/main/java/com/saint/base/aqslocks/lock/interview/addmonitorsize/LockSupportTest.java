package com.saint.base.aqslocks.lock.interview.addmonitorsize;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-02-03 22:03
 */
public class LockSupportTest {
    volatile List lists = new ArrayList();

    public void add(Object o) {
        lists.add(o);
    }

    public Integer getSize() {
        return lists.size();
    }

    static Thread t1 = null;
    static Thread t2 = null;

    public static void main(String[] args) {
        LockSupportTest l = new LockSupportTest();
        final Object lock = new Object();
        t1 = new Thread(() -> {
            System.out.println("添加线程开始");
            for (int i = 0; i < 10; i++) {
                l.add(new Object());
                System.out.println("添加数据" + i);
                if (l.getSize() == 5) {
                    LockSupport.unpark(t2);
                    LockSupport.park();
                }
            }
            System.out.println("添加线程结束");
        }, "t1");

        t2 = new Thread(() -> {
            System.out.println("监控线程开始");
            if (l.getSize() != 5) {
                LockSupport.park();
            }
            System.out.println("监控线程结束");
            LockSupport.unpark(t1);
        }, "t2");

        t1.start();
        t2.start();
    }
}
