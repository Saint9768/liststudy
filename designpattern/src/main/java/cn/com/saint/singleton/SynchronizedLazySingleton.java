package cn.com.saint.singleton;

/**
 * 线程安全的懒汉式
 * 缺点：每次调用都要进行同步判断，造成不必要的同步开销，所以不建议使用。
 *
 * @author Saint
 * @createTime 2020-02-27 19:13
 */
public class SynchronizedLazySingleton {
    private static SynchronizedLazySingleton singleton;

    private SynchronizedLazySingleton() {
    }

    public synchronized SynchronizedLazySingleton getSingleton() {
        if (singleton == null) {
            singleton = new SynchronizedLazySingleton();
        }
        return singleton;
    }
}
