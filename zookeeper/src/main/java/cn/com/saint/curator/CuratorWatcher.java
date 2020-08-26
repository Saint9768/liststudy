package cn.com.saint.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
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
public class CuratorWatcher {

    CountDownLatch countDownLatch = new CountDownLatch(1);
    String ip = "192.168.240.111:2181";
    CuratorFramework client;


    @Test
    public void watcher1() throws Exception {
        // 监视某个节点的数据变化
        // arg1：连接对象
        // arg2：监视的节点路径
        NodeCache nodeCache = new NodeCache(client, "/watcher1");
        // 启动监视器对象
        nodeCache.start();
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            // 节点变化时回调的方法
            @Override
            public void nodeChanged() throws Exception {
                System.out.println(nodeCache.getCurrentData().getPath());
                System.out.println(new String(nodeCache.getCurrentData().getData()));
            }
        });
        Thread.sleep(60000);
        System.out.print("结束");
        //关闭监视器对象
        nodeCache.close();
    }

    @Test
    public void watcher2() throws Exception {
        // 监听子节点的变化
        // arg1：连接对象
        // arg2: 监视的节点路径
        // arg3: 事件中是否可以获取节点的数据
        PathChildrenCache pathChildrenCache = new PathChildrenCache(client, "/watcher1", true);
        //启动监听
        pathChildrenCache.start();
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            // 当子节点变化时回调的方法
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                // 节点的时间类型
                System.out.println(pathChildrenCacheEvent.getType());
                // 节点的路径
                System.out.println(pathChildrenCacheEvent.getData().getPath());
                // 节点数据
                System.out.println(new String(pathChildrenCacheEvent.getData().getData()));
            }
        });
        Thread.sleep(60000);
        System.out.print("结束");
        // 关闭监听
        pathChildrenCache.close();
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
