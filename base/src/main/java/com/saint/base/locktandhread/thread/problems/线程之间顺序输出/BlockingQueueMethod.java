package com.saint.base.locktandhread.thread.problems.线程之间顺序输出;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-02-26 8:11
 */
public class BlockingQueueMethod {

    static BlockingQueue<String> queue1 = new ArrayBlockingQueue<>(1);
    static BlockingQueue<String> queue2 = new ArrayBlockingQueue<>(1);

    public static void main(String[] args) {
        char[] aI = new char[]{'1', '2', '3', '4', '5'};
        char[] aC = new char[]{'a', 'b', 'c', 'd', 'e'};
        new Thread(() -> {
            for (char c : aI) {
                System.out.print(c + " ");
                try {
                    queue1.put("ok");
                    queue2.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

        new Thread(() -> {
            for (char c : aC) {
                try {
                    queue1.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print(c + " ");
                try {
                    queue2.put("ok");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t2").start();
    }
}
