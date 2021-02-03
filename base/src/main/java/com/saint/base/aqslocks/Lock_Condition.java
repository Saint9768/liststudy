package com.saint.base.aqslocks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock锁的细粒化控制Condition
 *
 * @author Saint
 * @createTime 2020-07-26 8:49
 */
public class Lock_Condition {
    private final Lock lock = new ReentrantLock();
    private final Condition condition1 = lock.newCondition();
    private final Condition condition2 = lock.newCondition();

    public void start1() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " 执行");
            condition1.await();
            System.out.println(Thread.currentThread().getName() + " start");
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getName() + " end");
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            lock.unlock();
        }
    }

    public void start2() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " 执行");
            condition2.await();
            System.out.println(Thread.currentThread().getName() + " start");
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getName() + " end");
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            lock.unlock();
        }
    }

    public void releaseAll() {
        lock.lock();
        condition1.signalAll();
        condition2.signalAll();
        lock.unlock();
    }

    public synchronized void release1() {
        lock.lock();
        condition1.signal();
        lock.unlock();
    }

    public synchronized void release2() {
        lock.lock();
        condition2.signal();
        lock.unlock();
    }

    public static void main(String[] args) {
        Lock_Condition t = new Lock_Condition();
        try {
            Thread thread1 = new Thread(() -> t.start1(), "Thread1");
            thread1.start();
            Thread.sleep(100);
            Thread thread2 = new Thread(() -> t.start2(), "Thread2");
            thread2.start();
            Thread.sleep(1000);
            t.release2();
            t.release1();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
