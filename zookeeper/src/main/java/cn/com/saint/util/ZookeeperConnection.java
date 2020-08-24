package cn.com.saint.util;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Zookeeper连接工具类
 *
 * @author Saint
 * @version 1.0
 * @createTime 2020-08-17 21:28
 */
public class ZookeeperConnection {
    public static void main(String[] args) {
        try {
            //计数器
            CountDownLatch countDownLatch = new CountDownLatch(1);
            // arg1：服务器的IP和端口
            // arg2: 客户端与服务器之间的会话超时时间，以毫秒为单位
            // arg3：监视器对象
            ZooKeeper zooKeeper = new ZooKeeper("192.168.240.111:2181,192.168.240.111:2182,192.168.240.111:2183",
                    5000, watchedEvent -> {
                if (watchedEvent.getState() == Watcher.Event.KeeperState.SyncConnected) {
                    System.out.println("连接创建成功");
                    countDownLatch.countDown();
                }
            });
            //主线程阻塞等待连接对象的创建成功
            countDownLatch.await();
            // 会话编号
            System.out.println(zooKeeper.getSessionId());
            zooKeeper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
