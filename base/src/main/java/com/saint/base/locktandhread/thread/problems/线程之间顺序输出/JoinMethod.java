package com.saint.base.locktandhread.thread.problems.线程之间顺序输出;

/**
 * 保证线程按顺序执行。主线程要等子线程执行完才继续执行。
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-02-20 22:16
 */
public class JoinMethod {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 4; i++) {
                System.out.println("线程：" + Thread.currentThread().getName() + "正在运行 " + i);
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 4; i++) {
                System.out.println("线程：" + Thread.currentThread().getName() + "正在运行 " + i);
            }
        }, "t2");

        Thread t3 = new Thread(() -> {
            try {
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 4; i++) {
                System.out.println("线程：" + Thread.currentThread().getName() + "正在运行 " + i);
            }
        }, "t3");
        t1.start();
        t2.start();
        t3.start();
    }
}
