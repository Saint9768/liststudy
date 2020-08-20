package cn.com.saint.watcher;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * 监控客户端和服务端的连接状态
 *
 * @author Saint
 * @version 1.0
 * @createTime 2020-08-20 7:44
 */
public class ZkConnectionWatcher implements Watcher {

    /**
     * 计数器对象
     */
    static CountDownLatch countDownLatch = new CountDownLatch(1);

    /**
     * 连接对象
     */
    static ZooKeeper zooKeeper;

    /**
     * 事件处理过程
     *
     * @param event
     */
    @Override
    public void process(WatchedEvent event) {
        try {
            // 事件类型
            if (event.getType() == Event.EventType.None) {
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    System.out.println("连接创建成功");
                    countDownLatch.countDown();
                } else if (event.getState() == Event.KeeperState.Disconnected) {
                    System.out.println("断开连接");
                } else if (event.getState() == Event.KeeperState.Expired) {
                    System.out.println("会话超时！");
                    //重新创建连接
                    zooKeeper = new ZooKeeper("120.26.187.17", 5000, new ZkConnectionWatcher());
                } else if (event.getState() == Event.KeeperState.AuthFailed) {
                    System.out.println("认证失败！");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            zooKeeper = new ZooKeeper("120.26.187.17", 5000, new ZkConnectionWatcher());
            // 阻塞线程，等待连接的创建
            countDownLatch.await();
            //打印会话ID
            System.out.println(zooKeeper.getSessionId());
            Thread.sleep(5000);
            zooKeeper.close();
            System.out.println("结束");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
