package cn.com.saint.singleton;

/**
 * 饿汉式单例模式
 * <p>
 * 单例应该只用来保存全局的状态，并不能和任何作用域绑定。如果这些状态的作用域比一个完整的应用程序的生命周期要短
 * 那么这个状态就不应该使用单例来管理。
 *
 * @author Saint
 * @createTime 2020-02-27 18:57
 */
public class HungrySingleton {

    private static HungrySingleton singleton = new HungrySingleton();

    private HungrySingleton() {
    }

    public static HungrySingleton getSingleton() {
        return singleton;
    }
}
