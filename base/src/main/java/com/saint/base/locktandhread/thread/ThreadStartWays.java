package com.saint.base.locktandhread.thread;

import java.util.concurrent.*;

/**
 * 线程的几种启动方式
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-02-02 9:22
 */
public class ThreadStartWays {
    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("Hello MyThread!");
        }
    }

    static class MyRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println("Hello MyRunnable!");
        }
    }

    static class MyCallable implements Callable {

        @Override
        public String call() throws Exception {
            return "call success!";
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 1、直接启动Thread实现类
        new MyThread().start();

        // 2.直接启动Runnable接口实现类
        new Thread(new MyRunnable()).start();

        // 3.箭头函数
        new Thread(() -> {
            System.out.println("Helo lambda!");
        }).start();

        // 4.FutureTask + Callable
        FutureTask<String> futureTask = new FutureTask<String>(new MyCallable());
        Thread t = new Thread(futureTask);
        t.start();
        System.out.println(futureTask.get());


        // 5. newCachedThreadPool线程池
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(() -> {
            System.out.println("Hello threadPool!");
        });
        service.shutdown();
    }
}
