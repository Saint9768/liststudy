package cn.com.saint.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 负责绑定观察者和client对象
 *
 * @author Saint
 * @version 1.0
 * @createTime 2020-10-08 13:21
 */
public class Subject {

    private final List<Observer> observers = new ArrayList<>();

    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        notifyAllObservers();
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

}
