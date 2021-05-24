package com.saint.base.locktandhread.thread.pool;

import java.util.concurrent.*;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-05-24 6:33
 */
public class ThreadPoolExecutorTest {
    public static void main(String[] args) throws InterruptedException {
        MyThreadPool myThreadPool = new MyThreadPool(1, 1, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r);
                        // 获取线程池中的异常
                        t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                            @Override
                            public void uncaughtException(Thread t, Throwable e) {
                                // 对异常进行处理
                                System.out.println("注意，出错了！");
                            }
                        });
                        return t;
                    }
                }, new ThreadPoolExecutor.AbortPolicy());
        myThreadPool.execute(() -> System.out.println("1"));
        myThreadPool.execute(() -> System.out.println("1"));
        myThreadPool.execute(() -> System.out.println("1"));
        myThreadPool.execute(() -> System.out.println("1"));
        myThreadPool.shutdown();
        // 等了一分钟，线程池中的任务还没跑完，主线程便结束。如果线程池中的任务很快就结束了，那么此处不会等1分钟
        myThreadPool.awaitTermination(1, TimeUnit.MINUTES);
    }
}

/**
 * threadPoolExecutor实现类，让afterExecute钩子函数出现异常
 */
class MyThreadPool extends ThreadPoolExecutor {

    public MyThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        int i = 1 / 0;
    }
}
