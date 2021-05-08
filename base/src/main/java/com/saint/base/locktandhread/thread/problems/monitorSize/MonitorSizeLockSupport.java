package com.saint.base.locktandhread.thread.problems.monitorSize;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * 监控容器容量
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-04-14 22:20
 */
public class MonitorSizeLockSupport {
    volatile List lists = new ArrayList();

    public void add(Object o) {
        lists.add(o);
    }

    public int getSize() {
        return lists.size();
    }

    static Thread t1 = null;

    static Thread t2 = null;

    public static void main(String[] args) {
        MonitorSizeLockSupport c = new MonitorSizeLockSupport();
        t1 = new Thread(() -> {
            System.out.println("添加线程开始");
            for (int i = 0; i < 10; i++) {
                c.add(i);
                System.out.println("添加了" + i);
                if (c.getSize() == 5) {
                    // 唤醒t2
                    LockSupport.unpark(t2);
                    LockSupport.park();
                }
            }
            System.out.println("添加线程结束");
        }, "t1");

        t2 = new Thread(() -> {
            if (c.getSize() != 5) {
                LockSupport.park();
            }
            LockSupport.unpark(t1);
            System.out.println("监控线程结束");
        }, "t2");

        t1.start();
        t2.start();

    }
}
