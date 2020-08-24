package cn.com.saint.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2020-08-25 6:56
 */
public class CuratorGet {

    CountDownLatch countDownLatch = new CountDownLatch(1);
    String ip = "120.26.187.17:2181";
    CuratorFramework client;

    @Test
    public void getChild1() throws Exception {
        List<String> list = client.getChildren().forPath("/node1");
        for (String child : list) {
            System.out.println(child);
        }
    }

    @Test
    public void getChild2() throws Exception {
        // 异步方式查看节点
        client.getChildren()
                // 异步回调接口
                .inBackground(new BackgroundCallback() {
                    @Override
                    public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                        // 节点的路径
                        System.out.println(curatorEvent.getPath());
                        // 事件类型
                        System.out.println(curatorEvent.getType());
                        // 数据
                        List<String> list = curatorEvent.getChildren();
                        for (String child : list) {
                            System.out.println(child);
                        }
                    }
                })
                .forPath("/node1");
        Thread.sleep(3000);
        System.out.println("结束");
    }

    @Test
    public void get1() throws Exception {
        byte[] bytes = client.getData().forPath("/node1");
        System.out.println(bytes.toString());
    }

    @Test
    public void get2() throws Exception {
        //读取数据时读取节点的属性
        Stat stat = new Stat();
        byte[] bytes = client.getData()
                .storingStatIn(stat)
                .forPath("/node1");
        System.out.println(new String(bytes));
        System.out.println(stat.getVersion());
    }

    @Test
    public void get3() throws Exception {
        // 异步方式查看节点
        client.getData()
                // 异步回调接口
                .inBackground(new BackgroundCallback() {
                    @Override
                    public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                        // 节点的路径
                        System.out.println(curatorEvent.getPath());
                        // 事件类型
                        System.out.println(curatorEvent.getType());
                        // 数据
                        System.out.println(curatorEvent.getData());
                    }
                })
                .forPath("/node1");
        Thread.sleep(3000);
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
