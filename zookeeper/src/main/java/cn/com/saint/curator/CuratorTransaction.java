package cn.com.saint.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.RetryOneTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2020-08-25 6:56
 */
public class CuratorTransaction {

    CountDownLatch countDownLatch = new CountDownLatch(1);
    String ip = "192.168.240.111:2181";
    CuratorFramework client;


    @Test
    public void trans1() throws Exception {
        // 开启事务
        client.inTransaction()
                .create().forPath("/node1", "node1".getBytes())
                .and()
                .setData().forPath("/node2", "node2".getBytes())
                .and()
                // 事务提交
                .commit();
    }

    @Before
    public void before() {
        RetryPolicy retryPolicy = new RetryOneTime(3000);
        client = CuratorFrameworkFactory.builder()
                //IP地址端口号
                .connectString(ip)
                .sessionTimeoutMs(30000)
                //会话超时的重试策略:超时3秒之后，重连一次
                .retryPolicy(retryPolicy)
                .build();
        // 2.打开链接
        client.start();
    }

    @After
    public void after() {
        client.close();
    }
}
