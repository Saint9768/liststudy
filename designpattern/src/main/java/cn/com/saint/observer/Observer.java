package cn.com.saint.observer;

/**
 * 抽象观察者类
 *
 * @author Saint
 * @version 1.0
 * @createTime 2020-10-08 13:22
 */
public abstract class Observer {
    protected Subject subject;

    public abstract void update();
}
