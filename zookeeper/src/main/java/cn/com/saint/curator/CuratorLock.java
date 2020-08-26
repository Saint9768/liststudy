package cn.com.saint.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.retry.RetryOneTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2020-08-25 6:56
 */
public class CuratorLock {

    CountDownLatch countDownLatch = new CountDownLatch(1);
    String ip = "192.168.240.111:2181";
    CuratorFramework client;


    @Test
    public void lcok1() throws Exception {
        // 排他锁
        // arg1: 连接对象
        // arg2: 节点路径
        InterProcessLock interProcessLock = new InterProcessMutex(client, "/lock1");
        System.out.println("等待获取锁对象！");
        // 获取锁
        interProcessLock.acquire();
        for (int i = 0; i < 10; i++) {
            Thread.sleep(2000);
            System.out.println(i);
        }
        // 释放锁
        interProcessLock.release();
        System.out.println("等待释放锁！");
    }

    @Test
    public void lcok2() throws Exception {
        // 读写锁
        InterProcessReadWriteLock interProcessReadWriteLock = new InterProcessReadWriteLock(client, "/lock1");
        // 1. 获取读锁对象
        InterProcessLock interProcessLock = interProcessReadWriteLock.readLock();
        System.out.println("等待获取锁对象！");
        // 获取锁
        interProcessLock.acquire();
        for (int i = 0; i < 10; i++) {
            Thread.sleep(2000);
            System.out.println(i);
        }
        // 释放锁
        interProcessLock.release();
        System.out.println("等待释放锁！");
    }

    @Test
    public void lcok3() throws Exception {
        // 读写锁
        InterProcessReadWriteLock interProcessReadWriteLock = new InterProcessReadWriteLock(client, "/lock1");
        // 2. 获取写锁对象
        InterProcessLock interProcessLock = interProcessReadWriteLock.writeLock();
        System.out.println("等待获取锁对象！");
        // 获取锁
        interProcessLock.acquire();
        for (int i = 0; i < 10; i++) {
            Thread.sleep(2000);
            System.out.println(i);
        }
        // 释放锁
        interProcessLock.release();
        System.out.println("等待释放锁！");
    }

    @Before
    public void before() {
        RetryPolicy retryPolicy = new RetryOneTime(3000);
        client = CuratorFrameworkFactory.builder()
                //IP地址端口号
                .connectString(ip)
                .sessionTimeoutMs(30000)
                //会话超时的重试策略:超时3秒之后，重连一次
                .retryPolicy(retryPolicy)
                .build();
        // 2.打开链接
        client.start();
    }

    @After
    public void after() {
        client.close();
    }
}
