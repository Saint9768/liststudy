package cn.com.saint.singleton;

/**
 * 静态内部类单例模式
 * 优点：即节省了资源、又保证了线程安全
 * 只有第一次调用getInstance方法时虚拟机加载SingletonHolder 并初始化singleton
 *
 * @author Saint
 * @createTime 2020-02-27 19:15
 */
public class StaticInnerClassSingleton {
    private StaticInnerClassSingleton() {

    }

    public StaticInnerClassSingleton getSingleton() {
        return SingletonHolder.singleton;
    }

    private static class SingletonHolder {
        private static final StaticInnerClassSingleton singleton = new StaticInnerClassSingleton();
    }
}
