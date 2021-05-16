package cn.com.saint.watcher;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2020-08-20 15:51
 */
public class ZkWatcherGetData {

    String ip = "192.168.3.222:2181";
    ZooKeeper zooKeeper = null;

    @Test
    public void watcherGetData1() throws Exception {
        //使用连接对象的监视器
        zooKeeper.getData("/node1", true, null);
        // 在此期间，/watcher1节点的新增、创建、删除都将被监控
        Thread.sleep(30000);
        System.out.println("结束");
    }

    @Test
    public void watcherGetData2() throws Exception {
        // 使用自定义Watcher
        zooKeeper.getData("/node1", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("自定义Watcher");
                System.out.println("path=" + event.getPath());
                System.out.println("eventType=" + event.getType());
            }
        }, null);
        Thread.sleep(30000);
        System.out.println("结束");
    }

    @Test
    public void watcherGetData3() throws Exception {
        //一直对某个节点监控
        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                try {
                    System.out.println("自定义一次性Watcher");
                    System.out.println("path=" + event.getPath());
                    System.out.println("eventType=" + event.getType());
                    zooKeeper.getData("/node1", this, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        // 使用自定义Watcher
        zooKeeper.getData("/node1", watcher, null);
        Thread.sleep(50000);
        System.out.println("结束");
    }

    @Test
    public void watcherGetData4() throws Exception {
        // 注册多个监听器对象
        zooKeeper.getData("/node1", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("1");
                System.out.println("path=" + event.getPath());
                System.out.println("eventType=" + event.getType());
            }
        }, null);
        zooKeeper.getData("/node1", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("2");
                System.out.println("path=" + event.getPath());
                System.out.println("eventType=" + event.getType());
            }
        }, null);
        Thread.sleep(50000);
        System.out.println("结束");
    }

    @Test
    public void asyncGetData() throws Exception {
        System.out.println("   ---   async start ----");
        zooKeeper.getData("/node1", false, new AsyncCallback.DataCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
                System.out.println("   ---   async call back ----");
                System.out.println("数据为： " + new String(data));
                System.out.println("传递的上下文为： " + ctx.toString());
            }
        }, "haha");

        System.out.println("   ---   async over ----");
    }

    @Before
    public void before() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);
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
