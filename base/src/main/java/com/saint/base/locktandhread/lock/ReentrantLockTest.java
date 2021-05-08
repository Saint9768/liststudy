package com.saint.base.locktandhread.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-03-02 20:40
 */
public class ReentrantLockTest {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.lock();

        lock.unlock();
    }
}
