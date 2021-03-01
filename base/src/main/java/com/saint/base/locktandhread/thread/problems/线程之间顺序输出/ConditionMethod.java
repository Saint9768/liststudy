package com.saint.base.locktandhread.thread.problems.线程之间顺序输出;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-02-25 22:08
 */
public class ConditionMethod {

    public static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) {
        char[] aI = new char[]{'1', '2', '3', '4', '5'};
        char[] aC = new char[]{'a', 'b', 'c', 'd', 'e'};
        Lock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();

        new Thread(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                lock.lock();
                for (char c : aI) {
                    System.out.print(c + " ");
                    condition2.signal();
                    condition1.await();
                }
                condition2.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t1`").start();

        new Thread(() -> {
            latch.countDown();
            try {
                lock.lock();
                for (char c : aC) {
                    System.out.print(c + " ");
                    condition1.signal();
                    condition2.await();
                }
                condition1.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t1`").start();
    }
}
