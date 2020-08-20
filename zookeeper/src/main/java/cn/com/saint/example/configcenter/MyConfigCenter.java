package cn.com.saint.example.configcenter;

import cn.com.saint.watcher.ZkConnectionWatcher;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2020-08-20 21:33
 */
public class MyConfigCenter implements Watcher {

    String ip = "120.26.187.17:2181";
    CountDownLatch countDownLatch = new CountDownLatch(1);
    static ZooKeeper zooKeeper;

    /**
     * 用于本地存储配置信息
     */
    private String url;
    private String username;
    private String password;

    /**
     * 构造方法
     */
    public MyConfigCenter() {
        initValue();
    }

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
                //当配置信息发生变化时，重新加载
            } else if (event.getType() == Event.EventType.NodeDataChanged) {
                initValue();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接zookeeper服务器，读取配置信息
     *
     * @return
     */
    public void initValue() {
        try {
            // 1.创建连接对象
            zooKeeper = new ZooKeeper(ip, 60000, this);
            //阻塞线程，等待zookeeper连接的创建成功
            countDownLatch.await();
            // 2.读取配置信息
            this.url = new String(zooKeeper.getData("/config/url", true, null));
            this.username = new String(zooKeeper.getData("/config/username", true, null));
            this.password = new String(zooKeeper.getData("/config/password", true, null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public static void main(String[] args) {
        try {
            MyConfigCenter myConfigCenter = new MyConfigCenter();
            for (int i = 1; i <= 20; i++) {
                Thread.sleep(5000);
                System.out.println("url: " + myConfigCenter.getUrl());
                System.out.println("username: " + myConfigCenter.getUsername());
                System.out.println("password:" + myConfigCenter.getPassword());
                System.out.println("#########################################");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
