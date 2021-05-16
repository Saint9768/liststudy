package cn.com.saint.watcher;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2020-08-20 15:51
 */
public class ZkWatcherExists {

    String ip = "192.168.3.222:2181";
    ZooKeeper zooKeeper = null;

    @Test
    public void watcherExist1() throws Exception {
        // arg2:使用连接对象中的watcher
        zooKeeper.exists("/watcher1", true);
        // 在此期间，/watcher1节点的新增、创建、删除都将被监控
        Thread.sleep(50000);
        System.out.println("结束");
    }

    @Test
    public void watcherExist2() throws Exception {
        // 使用自定义Watcher
        zooKeeper.exists("/watcher1", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("自定义Watcher");
                System.out.println("path=" + event.getPath());
                System.out.println("eventType=" + event.getType());
            }
        });
        Thread.sleep(50000);
        System.out.println("结束");
    }

    @Test
    public void watcherExist3() throws Exception {
        //一直对某个节点监控
        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                try {
                    System.out.println("自定义一次性Watcher");
                    System.out.println("path=" + event.getPath());
                    System.out.println("eventType=" + event.getType());
                    zooKeeper.exists("/watcher1", this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        // 使用自定义Watcher
        zooKeeper.exists("/watcher1", watcher);
        Thread.sleep(50000);
        System.out.println("结束");
    }

    @Test
    public void watcherExist4() throws Exception {
        // 注册多个监听器对象
        zooKeeper.exists("/watcher1", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println(1);
                System.out.println("path=" + event.getPath());
                System.out.println("eventType=" + event.getType());
            }
        });
        zooKeeper.exists("/watcher1", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println(2);
                System.out.println("path=" + event.getPath());
                System.out.println("eventType=" + event.getType());
            }
        });
        Thread.sleep(50000);
        System.out.println("结束");
    }

    @Before
    public void before() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        // 第一种watcher， new zookeeper时，会触发一个session级别的callback
        //连接Zookeeper客户端
        zooKeeper = new ZooKeeper(ip, 6000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("连接对象的参数！");
                //连接成功
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    countDownLatch.countDown();
                }
                System.out.println("path=" + event.getPath());
                System.out.println("eventType=" + event.getType());
            }
        });
        countDownLatch.await();
    }

    @After
    public void after() throws Exception {
        zooKeeper.close();
    }
}
