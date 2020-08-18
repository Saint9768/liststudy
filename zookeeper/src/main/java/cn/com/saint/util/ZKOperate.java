package cn.com.saint.util;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 新增ZK节点
 *
 * @author Saint
 * @version 1.0
 * @createTime 2020-08-17 21:53
 */
public class ZKOperate {

    private String IP = "120.26.187.17";
    ZooKeeper zooKeeper;

    @Test
    public void exist2() throws Exception {
        zooKeeper.exists("/create/node1", false, (rc, path, ctx, stat) -> {
            System.out.println(rc);
            System.out.println(path);
            System.out.println(ctx);
            System.out.println(stat.getVersion());
        }, "I am content");
        System.out.println("结束????");
    }

    @Test
    public void exist1() throws Exception {
        Stat exists = zooKeeper.exists("/create/node133", false);
        System.out.println(exists);
    }


    @Test
    public void getChildren2() throws Exception {
        zooKeeper.getChildren("/create", false, new AsyncCallback.Children2Callback() {
            @Override
            public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
                System.out.println(rc);
                System.out.println(ctx);
                //子节点信息
                for (String s : children) {
                    System.out.println(s);
                }
            }
        }, "I am content");
        System.out.println("结束 ??");
    }

    @Test
    public void getChildren1() throws Exception {
        List<String> children = zooKeeper.getChildren("/create", false);
        for (String s : children) {
            System.out.println(s);
        }
    }

    @Test
    public void get2() throws Exception {
        zooKeeper.getData("/create/node1", false, new AsyncCallback.DataCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
                // 0代表读取成功
                System.out.println(rc);
                System.out.println(path);
                System.out.println(ctx);
                System.out.println(new String(data));
                System.out.println(stat.getVersion());
            }
        }, "I am content");
        System.out.println("结束????");
    }

    @Test
    public void get1() throws Exception {
        Stat stat = new Stat();
        //arg3: 读取节点属性的对象
        byte[] data = zooKeeper.getData("/create/node1", false, stat);
        //打印数据
        System.out.println(new String(data));
        //版本信息
        System.out.println(stat.getVersion());
    }

    @Test
    public void delete1() throws Exception {
        zooKeeper.delete("/create/node2", -1);
    }

    @Test
    public void delete2() throws Exception {
        zooKeeper.delete("/create/node2", 0, new AsyncCallback.VoidCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx) {
                System.out.println(rc);
                System.out.println(path);
                System.out.println(ctx);
            }
        }, "I am content");
        System.out.println("结束？？？");
    }

    @Test
    public void update1() throws Exception {
        // arg3:数据版本号，-1代表版本号不参与更新
        Stat stat = zooKeeper.setData("/create/node1", "node11".getBytes(), -1);
        // 当前节点的版本号
        System.out.println(stat.getAversion());
    }

    @Test
    public void update2() throws Exception {
        // arg3:数据版本号，-1代表版本号不参与更新
        zooKeeper.setData("/create/node1", "node33".getBytes(), 2, new AsyncCallback.StatCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, Stat stat) {
                // 0表示操作成功
                System.out.println(rc);
                //节点路径
                System.out.println(path);
                //上下文
                System.out.println(ctx);
                //节点状态
                System.out.println(stat.getVersion());
            }
        }, "I am content");
        Thread.sleep(1000);
        System.out.println("结束");
    }

    @Test
    public void create1() throws Exception {
        //arg3：权限列表 world:anyone:cdwra
        //arg4：节点的类型 PERSISTENT --> 持久化节点
        zooKeeper.create("/create/node1", "node1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    @Test
    public void create2() throws Exception {
        //arg3：权限列表 world:anyone:r
        zooKeeper.create("/create/node2", "node2".getBytes(), ZooDefs.Ids.READ_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    @Test
    public void create3() throws Exception {
        // world授权模式
        // 权限列表
        List<ACL> acls = new ArrayList<>();
        //授权模式和授权对象
        // 1. world授权模式
        //Id id = new Id("world", "anyone");
        // 2. IP授权模式
        Id id = new Id("ip", "120.26.187.17");
        // 权限设置
        acls.add(new ACL(ZooDefs.Perms.READ, id));
        acls.add(new ACL(ZooDefs.Perms.WRITE, id));
        acls.add(new ACL(ZooDefs.Perms.ADMIN, id));
        zooKeeper.create("/create/node4", "node4".getBytes(), acls, CreateMode.PERSISTENT);
    }

    @Test
    public void create5() throws Exception {
        // 3. auth授权模式
        zooKeeper.addAuthInfo("digest", "saint:123456".getBytes());
        zooKeeper.create("/create/node5", "node5".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
    }

    @Test
    public void create6() throws Exception {
        zooKeeper.addAuthInfo("digest", "zhouxin:123456".getBytes());
        List<ACL> acls = new ArrayList<>();
        // 3. auth授权模式
        Id id = new Id("auth", "zhouxin");
        acls.add(new ACL(ZooDefs.Perms.ALL, id));
        zooKeeper.create("/create/node6", "node6".getBytes(), acls, CreateMode.PERSISTENT);
    }

    @Test
    public void create7() throws Exception {
        List<ACL> acls = new ArrayList<>();
        // 4. digest授权模式 ,使用加密后的密码
        Id id = new Id("digest", "zhou:m4GVfwhKPisNTTI5ybLXRW26+54=");
        acls.add(new ACL(ZooDefs.Perms.ALL, id));
        zooKeeper.create("/create/node7", "node7".getBytes(), acls, CreateMode.PERSISTENT);
    }

    @Test
    public void create8() throws Exception {
        zooKeeper.create("/create/node8", "node8".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL,
                new AsyncCallback.StringCallback() {
                    @Override
                    public void processResult(int rc, String path, Object ctx, String name) {
                        //0代表创建成功
                        System.out.println(rc);
                        // 节点路径
                        System.out.println(path);
                        //节点名称
                        System.out.println(name);
                        //上下文参数
                        System.out.println(ctx);
                    }
                }, "I am context");
        Thread.sleep(1000);
        System.out.println("结束");
    }


    @Before
    public void before() throws Exception {
        //计数器
        CountDownLatch countDownLatch = new CountDownLatch(1);
        // arg1：服务器的IP和端口
        // arg2: 客户端与服务器之间的会话超时时间，以毫秒为单位
        // arg3：监视器对象
        zooKeeper = new ZooKeeper(IP, 5000, watchedEvent -> {
            if (watchedEvent.getState() == Watcher.Event.KeeperState.SyncConnected) {
                System.out.println("连接创建成功");
                countDownLatch.countDown();
            }
        });
        //主线程阻塞等待连接对象的创建成功
        countDownLatch.await();
        // 会话编号
        System.out.println(zooKeeper.getSessionId());
    }

    @After
    public void after() throws Exception {
        zooKeeper.close();
        System.out.println("After");
    }
}
