package com.saint.base.forkjoin;

import java.util.concurrent.*;

/**
 * @author Saint
 * @createTime 2020-07-26 20:53
 */
public class ForkJoinWork extends RecursiveTask<Long> {
    private Long start;//起始值
    private Long end;//结束值
    public static final Long critical = 10000L;//临界值

    public ForkJoinWork(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        //判断是否是拆分完毕
        //起始值差值
        Long lenth = end - start;
        if (lenth <= critical) {
            //如果拆分完毕就相加
            Long sum = 0L;
            for (Long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            //没有拆分完毕就开始拆分
            Long middle = (end + start) / 2;
            ForkJoinWork right = new ForkJoinWork(start, middle);
            right.fork();//拆分，并压入线程队列
            ForkJoinWork left = new ForkJoinWork(middle + 1, end);
            left.fork();//拆分，并压入线程队列
            //合并
            return right.join() + left.join();
        }

    }

    public static void main(String[] args) {
        //ForkJoin实现
        long l = System.currentTimeMillis();
        //实现ForkJoin 就必须有ForkJoinPool的支持
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        //参数为起始值与结束值
        ForkJoinTask<Long> task = new ForkJoinWork(0L, 100000L);
        Future<Long> result = forkJoinPool.submit(task);
        //获取结果
        try {
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        long l1 = System.currentTimeMillis();
        System.out.println("用时: " + (l1 - l));
        // ime: 8
    }
}