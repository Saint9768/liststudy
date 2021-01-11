package cn.com.saint.observer.weather;

import cn.com.saint.observer.Observer;

/**
 * 主题接口
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-11 21:39
 */
public interface SubjectS {

    /**
     * 注册观察者
     *
     * @param o
     */
    void registerObserver(ObserverS o);

    /**
     * 移除观察者
     *
     * @param o
     */
    void removeObserver(ObserverS o);

    /**
     * 主题发生改变时，通知观察者
     */
    void notifyObservers();
}
