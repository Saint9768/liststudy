package com.saint.base.locktandhread.thread.problems.线程之间顺序输出;

import java.util.concurrent.locks.LockSupport;

/**
 * 这个是首选
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-02-20 22:31
 */
public class LockSupportMethod {

    static Thread t1, t2;

    public static void main(String[] args) {
        char[] aI = new char[]{'1', '2', '3', '4', '5'};
        char[] aC = new char[]{'a', 'b', 'c', 'd', 'e'};

        t1 = new Thread(() -> {
            for (char c : aI) {
                LockSupport.park();
                System.out.print(c + " ");
                LockSupport.unpark(t2);
            }
        }, "t1");

        t2 = new Thread(() -> {
            for (char c : aC) {
                System.out.print(c + " ");
                LockSupport.unpark(t1);
                LockSupport.park();
            }
        }, "t1");
        t1.start();
        t2.start();
    }
}
