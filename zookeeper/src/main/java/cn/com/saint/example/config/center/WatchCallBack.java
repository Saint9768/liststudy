package cn.com.saint.example.config.center;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 回调函数
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-05-17 6:21
 */
@Slf4j
public class WatchCallBack implements Watcher, AsyncCallback.DataCallback, AsyncCallback.StatCallback {

    private ZooKeeper zk;

    private String watchPath;

    private MyConf myConf;

    private CountDownLatch init;

    /**
     * 同步阻塞，等待回调方法返回
     */
    public void aWait() {
        zk.exists(watchPath, this, this, "initExists");
        try {
            init.await();
        } catch (InterruptedException e) {
            log.error("Error int wait countDownLatch, ", e);
        }
    }

    public ZooKeeper getZk() {
        return zk;
    }


    public String getWatchPath() {
        return watchPath;
    }


    public void setInit(int init) {
        log.info("Set init count: {}", init);
        this.init = new CountDownLatch(init);
    }

    public MyConf getMyConf() {
        return myConf;
    }

    public void setMyConf(MyConf myConf) {
        this.myConf = myConf;
    }

    public void setWatchPath(String watchPath) {
        this.watchPath = watchPath;
    }

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }

    /**
     * DataCallBack回调实现方法
     */
    @Override
    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
        if (!Objects.isNull(data)) {
            myConf.setConf(new String(data));
            init.countDown();
        }
    }

    /**
     * StatCallBack回调实现方法
     */
    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        if (stat != null) {
            zk.getData(watchPath, this, this, "ex");
        }
    }

    /**
     * 监视器方法实现
     *
     * @param event
     */
    @Override
    public void process(WatchedEvent event) {
        Event.EventType type = event.getType();
        log.info(event.toString());
        switch (type) {
            case NodeCreated:
                log.info("...watch@Created");
                zk.getData(watchPath, this, this, "NodeCreated");
                break;
            case NodeDeleted:
                myConf.setConf("");
                init = new CountDownLatch(1);
                break;
            case NodeDataChanged:
                zk.getData(watchPath, this, this, "NodeChanged");
                break;
        }
    }
}
