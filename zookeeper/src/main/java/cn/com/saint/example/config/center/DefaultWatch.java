package cn.com.saint.example.config.center;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.CountDownLatch;

/**
 * ZK初始化时使用的监视器
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-05-17 6:16
 */
@Slf4j
@Data
@ToString
public class DefaultWatch implements Watcher {

    /**
     * 用来控制ZK初始化，只有ZK初始化好，才能进行后续操作
     */
    private CountDownLatch init;

    public CountDownLatch getInit() {
        return init;
    }

    public void setInit(CountDownLatch init) {
        this.init = init;
    }

    @Override
    public void process(WatchedEvent event) {
        Event.KeeperState state = event.getState();
        switch (state) {
            case Disconnected:
                log.info("Disconnected zk and new countDownLatch!");
                init = new CountDownLatch(1);
                break;

            case SyncConnected:
                log.info("Connected zk and countDown");
                init.countDown();
                break;

        }

    }
}
