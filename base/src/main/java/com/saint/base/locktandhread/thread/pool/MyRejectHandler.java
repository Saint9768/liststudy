package com.saint.base.locktandhread.thread.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-02-28 15:10
 */
@Slf4j
public class MyRejectHandler {
    public static void main(String[] args) {

        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 10, 6000, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(10),
                Executors.defaultThreadFactory(),
                new MyHandler());
        pool.shutdown();
        try {
            //等待队列中的任务执行完毕，但是只等指定时间
            pool.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    static class MyHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            log.info("r rejected");
            // save r kafka mysql redis
            // try 3 times
            if (executor.getQueue().size() > 10000) {
                // try put again();
            }
        }
    }
}
