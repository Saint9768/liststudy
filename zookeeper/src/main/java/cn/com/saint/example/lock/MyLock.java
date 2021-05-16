package cn.com.saint.example.lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Zookeeper分布式锁
 *
 * @author Saint
 * @version 1.0
 * @createTime 2020-08-21 9:32
 */
public class MyLock {
    String ip = "192.168.3.222:2181";
    CountDownLatch countDownLatch = new CountDownLatch(1);

    /**
     * Zookeeper配置信息
     */
    ZooKeeper zooKeeper;
    private static final String LOCK_ROOT_PATH = "/Locks";
    private static final String LOCK_NODE_NAME = "Lock_";
    private String lockPath;

    /**
     * 监视器对象，监视上一个节点是否被删除
     */
    Watcher watcher = new Watcher() {
        @Override
        public void process(WatchedEvent event) {
            if (event.getType() == Event.EventType.NodeDeleted) {
                synchronized (this) {
                    notifyAll();
                }
            }
        }
    };

    public MyLock() {
        try {
            zooKeeper = new ZooKeeper(ip, 30000, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getType() == Event.EventType.None) {
                        if (event.getState() == Event.KeeperState.SyncConnected) {
                            System.out.println("连接创建成功！");
                            countDownLatch.countDown();
                        }
                    }
                }
            });
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取锁
     *
     * @throws Exception
     */
    public void acquireLock() throws Exception {
        // 1.创建锁节点
        createLock();
        // 2.尝试获取锁
        attemptLock();
    }

    /**
     * 创建锁节点
     */
    private void createLock() throws Exception {
        // 1.判断Locks节点是否存在，不存在则创建
        Stat exists = zooKeeper.exists(LOCK_ROOT_PATH, false);
        //节点不存在则创建
        if (exists == null) {
            zooKeeper.create(LOCK_ROOT_PATH, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        // 2.创建临时有序节点
        lockPath = zooKeeper.create(LOCK_ROOT_PATH + "/" + LOCK_NODE_NAME, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT_SEQUENTIAL);
        System.out.println("节点创建成功：" + lockPath);

    }

    /**
     * 尝试获取锁
     */
    private void attemptLock() throws Exception {
        // 1.获取/Locks节点下的所有子节点
        List<String> list = zooKeeper.getChildren(LOCK_ROOT_PATH, false);
        // 2.对子节点进行排序
        Collections.sort(list);
        // /Locks/Lock_0000000001
        int index = list.indexOf(lockPath.substring(LOCK_ROOT_PATH.length() + 1));
        if (index == 0) {
            System.out.println("获取锁成功");
            return;
        } else {
            //上一个节点的路径
            String path = list.get(index - 1);
            Stat stat = zooKeeper.exists(LOCK_ROOT_PATH + "/" + path, watcher);
            // 可能上一个节点已经释放了锁
            if (stat == null) {
                attemptLock();
            } else {
                //上一个节点还没获取到锁 / 释放锁
                synchronized (watcher) {
                    watcher.wait();
                }
                attemptLock();
            }
        }

    }

    /**
     * s
     * 释放锁
     *
     * @throws Exception
     */
    public void releaseLock() throws Exception {
        // 删除临时有序节点
        zooKeeper.delete(this.lockPath, -1);
        zooKeeper.close();
        System.out.println("锁已经释放：" + this.lockPath);
    }

    public static void main(String[] args) throws Exception {
        MyLock lock = new MyLock();
        lock.createLock();
    }
}
