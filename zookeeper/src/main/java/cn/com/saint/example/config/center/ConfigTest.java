package cn.com.saint.example.config.center;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-05-17 6:40
 */
@Slf4j
public class ConfigTest {
    ZooKeeper zk;
    ZkConf zkConf;
    DefaultWatch watch;
    MyConf myConf = new MyConf();
    WatchCallBack watchCallBack = new WatchCallBack();

    @Before
    public void conn() {
        zkConf = new ZkConf();
        zkConf.setAddress("192.168.3.222:2181/config");
        zkConf.setSessionTime(10000);
        watch = new DefaultWatch();
        ZkUtils.setWatch(watch);
        ZkUtils.setZkConf(zkConf);
        zk = ZkUtils.getZk();
    }

    @After
    public void close() {
        ZkUtils.close();
    }

    @Test
    public void testGetZkConfigInfo() {
        //程序的配置来源：本地文件系统，数据库，redis，zk。。一切程序可以连接的地方
        //配置内容的提供、变更、响应：本地，数据库等等，都是需要心跳判断，或者手工调用触发

        //我是程序A 我需要配置：1，zk中别人是不是填充了配置；2，别人填充、更改了配置之后我怎么办
        watchCallBack.setInit(1);
        watchCallBack.setMyConf(myConf);
        watchCallBack.setWatchPath("/appConf");
        watchCallBack.setZk(zk);

        try {
            watchCallBack.aWait();
            while (true) {
                if (StringUtils.isEmpty(myConf.getConf())) {
                    log.info("Configuration is Empty!");
                    watchCallBack.aWait();
                } else {
                    System.out.println("Configuration info： " + myConf.getConf());
                }
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
