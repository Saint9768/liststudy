package cn.com.saint.example.config.center;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * ZooKeeper工具类
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-05-17 6:32
 */
@Slf4j
public class ZkUtils {

    private static ZooKeeper zk;

    private static ZkConf zkConf;

    private static DefaultWatch watch;

    private static final CountDownLatch c = new CountDownLatch(1);

    public static void setZkConf(ZkConf zkConf) {
        ZkUtils.zkConf = zkConf;
    }

    public static void setWatch(DefaultWatch watch) {
        ZkUtils.watch = watch;
        // 将CountDownLatch传递给DefaultWatch
        watch.setInit(c);
    }

    public static ZooKeeper getZk() {
        try {
            zk = new ZooKeeper(zkConf.getAddress(), zkConf.getSessionTime(), watch);
            // 只有zk建立好连接，才会继续往下走
            c.await();
        } catch (Exception e) {
            log.error("Error in connecting zookeeper, ", e);
        }
        return zk;
    }

    public static void close() {
        try {
            zk.close();
        } catch (InterruptedException e) {
            log.error("Error to closing ZooKeeper, ", e);
        }
    }

}
