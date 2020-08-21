package cn.com.saint.example.lock;

/**
 * 分布式锁测试类
 * 运行两遍程序，看日志输出
 *
 * @author Saint
 * @version 1.0
 * @createTime 2020-08-21 10:02
 */
public class TicketSeller {

    public void sell() {
        System.out.println("售票开始！");
        //线程随机休眠数毫秒，模拟现实中的费时操作
        int sleepMillis = 3000;
        try {
            Thread.sleep(sleepMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("售票结束");
    }

    public void sellTicketWithLock() throws Exception {
        MyLock lock = new MyLock();
        //获取锁
        lock.acquireLock();
        sell();
        //释放锁
        lock.releaseLock();
    }

    public static void main(String[] args) throws Exception {
        TicketSeller seller = new TicketSeller();
        for (int i = 0; i < 10; i++) {
            seller.sellTicketWithLock();
        }
    }
}
