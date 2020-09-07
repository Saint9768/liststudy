package cn.com.saint.example.dislock;

import java.util.concurrent.locks.Lock;

/**
 * @author Saint
 */
public class Test {
    /**
     * 100张票
     */
    private Integer n = 100;

    public void printInfo() {
        System.out.println(Thread.currentThread().getName() +
                "正在运行,剩余余票:" + --n);
    }

    public class TicketThread implements Runnable {
        @Override
        public void run() {
            Lock lock = new DistributedLock("120.26.187.17:2181", "zk");
            lock.lock();
            try {
                if (n > 0) {
                    printInfo();
                }
            } finally {
                lock.unlock();
            }
        }
    }

    public void ticketStart() {
        TicketThread thread = new TicketThread();
        for (int i = 0; i < 30; i++) {
            Thread t = new Thread(thread, "mem" + i);
            t.start();
        }
    }

    public static void main(String[] args) {
        new Test().ticketStart();
    }
}
