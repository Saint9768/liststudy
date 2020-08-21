package cn.com.saint.example;

import cn.com.saint.watcher.ZkConnectionWatcher;
import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2020-08-21 9:11
 */
public class GlobalUniqueId implements Watcher {
    String ip = "120.26.187.17:2181";
    CountDownLatch countDownLatch = new CountDownLatch(1);
    /**
     * 用户生成序号的节点
     */
    String defaultPath = "/uniqueId";
    ZooKeeper zooKeeper;

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
                    zooKeeper = new ZooKeeper("120.26.187.17:2181", 60000, new ZkConnectionWatcher());
                } else if (event.getState() == Event.KeeperState.AuthFailed) {
                    System.out.println("认证失败！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GlobalUniqueId() {
        try {
            //打开链接
            zooKeeper = new ZooKeeper(ip, 60000, this);
            // 阻塞线程等待连接的创建成功
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成ID的方法
     *
     * @return
     */
    public String getUniqueId() {
        String path = "";
        try {
            // 创建临时有序接节点
            path = zooKeeper.create(defaultPath, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL_SEQUENTIAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // /uniqueId0000000010
        return path.substring(9);
    }

    public static void main(String[] args) {
        GlobalUniqueId g = new GlobalUniqueId();
        for (int i = 1; i <= 5; i++) {
            String id = g.getUniqueId();
            System.out.println(id);
        }
    }
}
