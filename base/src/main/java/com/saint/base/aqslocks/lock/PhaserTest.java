package com.saint.base.aqslocks.lock;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * 结合了CountDownLatch和CyclicBarrier
 * 分阶段 批量干活
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-02-02 22:25
 */
public class PhaserTest {
    static Random r = new Random();

    static MarriagePhaser phaser = new MarriagePhaser();

    public static void main(String[] args) {
        // 一共几个人
        phaser.bulkRegister(7);

        // 5个客人
        for (int i = 0; i < 5; i++) {
            new Thread(new Person("p" + i)).start();
        }

        new Thread(new Person("新郎")).start();
        new Thread(new Person("新娘")).start();
    }

    static void milliSleep(int milli) {
        try {
            TimeUnit.MILLISECONDS.sleep(milli);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Phaser实现类
     */
    static class MarriagePhaser extends Phaser {
        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            switch (phase) {
                case 0:
                    System.out.println("所有人都到齐了！" + registeredParties);
                    System.out.println();
                    return false;
                case 1:
                    System.out.println("所有人都吃完了！" + registeredParties);
                    System.out.println();
                    return false;
                case 2:
                    System.out.println("所有人都离开了！" + registeredParties);
                    System.out.println();
                    return false;
                case 3:
                    System.out.println("婚礼结束！入洞房？" + registeredParties);
                    return true;
                default:
                    return true;
            }
        }
    }

    static class Person implements Runnable {
        String name;

        public Person(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            arrive();
            eat();
            leave();
            hug();
        }

        private void hug() {
            if ("新郎".equals(name) || "新娘".equals(name)) {
                milliSleep(r.nextInt(1000));
                System.out.printf("%s 洞房！ \n", name);
                // 在栅栏前停住
                phaser.arriveAndAwaitAdvance();
            } else {
                // 其他人不参与，移除
                phaser.arriveAndDeregister();
            }
        }

        private void leave() {
            milliSleep(r.nextInt(1000));
            System.out.printf("%s 离开！ \n", name);
            // 在栅栏前停住
            phaser.arriveAndAwaitAdvance();
        }

        private void eat() {
            milliSleep(r.nextInt(1000));
            System.out.printf("%s 吃完！ \n", name);
            // 在栅栏前停住
            phaser.arriveAndAwaitAdvance();
        }

        private void arrive() {
            milliSleep(r.nextInt(1000));
            System.out.printf("%s 到达现场！ \n", name);
            // 在栅栏前停住
            phaser.arriveAndAwaitAdvance();
        }
    }
}