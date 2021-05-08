package com.saint.base.locktandhread.thread.pool;

import org.springframework.util.StopWatch;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 假设你能够提供一个服务
 * 这个服务能查询各大电商网站同一类产品的价格。
 * CompletableFuture --> 用来管理多个future的结果。
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-04-13 21:16
 */
public class CompletableFutureTest {

    private static void delay() {
        int time = new Random().nextInt(500);
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            // TODO
        }
        System.out.printf("After %s sleep! \n", time);
    }

    private static double priceOfTB() {
        delay();
        return 1.0;
    }

    private static double priceOfTM() {
        delay();
        return 2.0;
    }

    private static double priceOfJD() {
        delay();
        return 3.0;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("statistics");

        CompletableFuture<Double> futureTB = CompletableFuture.supplyAsync(() -> priceOfTB());
        CompletableFuture<Double> futureTM = CompletableFuture.supplyAsync(() -> priceOfTM());
        CompletableFuture<Double> futureJD = CompletableFuture.supplyAsync(() -> priceOfJD());

        // 等上述三个异步方法都跑完之后汇总结果。
        CompletableFuture.allOf(futureJD, futureTB, futureTM).join();

        // 异步执行一个方法，并对结果做相应操作
        CompletableFuture.supplyAsync(() -> priceOfTM())
                .thenApply(String::valueOf)
                .thenApply(s -> "price: " + s)
                .thenAccept(System.out::println);

        System.out.println("---------------------------");
        System.out.println(futureJD.get());
        System.out.println(futureTM.get());
        System.out.println(futureTB.get());
        stopWatch.stop();
        System.out.println("共耗时： " + stopWatch.getLastTaskTimeMillis());
    }
}
