package cn.com.saint.singleton;

/**
 * 懒汉式单例
 * 缺点：线程不安全。
 * 优点：节省了资源
 *
 * @author Saint
 * @createTime 2020-02-27 19:03
 */
public class LazySingleton {
    private static LazySingleton singleton;

    private LazySingleton() {
    }

    public LazySingleton getSingleton() {
        if (singleton == null) {
            singleton = new LazySingleton();
        }
        return singleton;
    }
}
