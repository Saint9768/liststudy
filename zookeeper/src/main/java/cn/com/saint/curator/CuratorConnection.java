package cn.com.saint.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.retry.RetryOneTime;
import org.apache.curator.retry.RetryUntilElapsed;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2020-08-25 6:42
 */
public class CuratorConnection {
    public static void main(String[] args) throws Exception {
        // session重连策略
        // 1）3秒重连一次，只重连一次
        //RetryPolicy retryPolicy = new RetryOneTime(3000);

        // 2）每3秒重连一次，重连3吃
        //RetryPolicy retryPolicy = new RetryNTimes(3,3000);

        // 3）每3秒重连一次，总等待时间超过10秒后停止重连
        //RetryPolicy retryPolicy = new RetryUntilElapsed(10000, 3000);

        // 4）随着重连次数的增加，重连的时间间隔也会增加
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);


        // 1.创建连接对象
        CuratorFramework client = CuratorFrameworkFactory.builder()
                //IP地址端口号
                .connectString("192.168.240.111:2181,192.168.240.111:2182")
                .sessionTimeoutMs(5000)
                //会话超时的重试策略:超时3秒之后，重连一次
                .retryPolicy(retryPolicy)
                .namespace("create").build();
        // 2.打开链接
        client.start();
        System.out.println(client.isStarted());
        // 3.关闭连接
        client.close();
    }
}
