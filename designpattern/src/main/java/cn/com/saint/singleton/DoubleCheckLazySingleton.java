package cn.com.saint.singleton;

/**
 * DCL双重检查锁
 * 优点：资源利用率高。
 * 缺点：第一次加载时反应稍慢一些，高并发环境下会有一些缺陷，例如DCL失效。
 * 所以在《java并发编程实践》一书推荐用静态内部类单例模式来替代DCL。
 *
 * @author Saint
 * @createTime 2020-02-27 19:04
 */
public class DoubleCheckLazySingleton {
    /**
     * 加volatile关键字用来防止指令重排序
     * 虽然volatile会影响性能，但为了程序的正确性还是值得的。
     */
    private volatile static DoubleCheckLazySingleton singleton;

    private DoubleCheckLazySingleton() {
    }

    public DoubleCheckLazySingleton getSingleton() {
        //第一步是为了不必要的同步
        if (singleton == null) {
            synchronized (this) {
                //只有在singleton为null的时候才创建实例
                if (singleton == null) {
                    singleton = new DoubleCheckLazySingleton();
                }
            }
        }
        return singleton;
    }
}
