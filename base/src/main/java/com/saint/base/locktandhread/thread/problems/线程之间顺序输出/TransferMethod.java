package com.saint.base.locktandhread.thread.problems.线程之间顺序输出;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-02-25 21:49
 */
public class TransferMethod {
    public static void main(String[] args) {

        char[] aI = new char[]{'1', '2', '3', '4', '5'};
        char[] aC = new char[]{'a', 'b', 'c', 'd', 'e'};

        TransferQueue<Character> transferQueue = new LinkedTransferQueue<>();
        new Thread(() -> {
            for (char c : aI) {
                try {
                    // take()会阻塞，知道队列里有数据
                    System.out.print(transferQueue.take() + " ");
                    transferQueue.transfer(c);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

        new Thread(() -> {
            for (char c : aC) {
                try {
                    transferQueue.transfer(c);
                    System.out.print(transferQueue.take() + " ");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t2").start();
    }
}
