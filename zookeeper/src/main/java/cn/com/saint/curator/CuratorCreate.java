package cn.com.saint.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2020-08-25 6:56
 */
public class CuratorCreate {

    CountDownLatch countDownLatch = new CountDownLatch(1);
    String ip = "120.26.187.17:2181";
    CuratorFramework client;


    @Test
    public void create1() throws Exception {
        // 新增节点
        client.create()
                .withMode(CreateMode.PERSISTENT)
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                .forPath("/node1", "node1".getBytes());
        System.out.println("结束");
    }

    @Test
    public void create2() throws Exception {
        //指定以权限列表
        List<ACL> list = new ArrayList<>();
        // 授权模式和授权对象
        Id id = new Id("ip", "120.26.187.17");
        list.add(new ACL(ZooDefs.Perms.ALL, id));
        client.create().withMode(CreateMode.PERSISTENT).withACL(list).forPath("/node2", "node2".getBytes());
        System.out.println("结束");

    }

    @Test
    public void create3() throws Exception {
        // 递归创建节点树
        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                .forPath("/node3/node31", "node1".getBytes());
        System.out.println("结束");
    }

    @Test
    public void create4() throws Exception {
        // 异步方式创建节点
        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                // 异步回调接口
                .inBackground(new BackgroundCallback() {
                    @Override
                    public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                        // 节点的路径
                        System.out.println(curatorEvent.getPath());
                        // 事件类型
                        System.out.println(curatorEvent.getType());
                    }
                })
                .forPath("/node4", "node4".getBytes());
        System.out.println("结束");
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
                .namespace("create").build();
        // 2.打开链接
        client.start();
    }

    @After
    public void after() {
        client.close();
    }
}
