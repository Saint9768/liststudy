package com.saint.base.locktandhread.thread.problems;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-02-20 22:15
 */
public class A {
    public static void main(String[] args) throws InterruptedException {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(10);
        queue.offer("aaa");
        queue.take();
    }
}
