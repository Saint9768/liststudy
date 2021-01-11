package cn.com.saint.observer.weather;

import cn.com.saint.observer.Observer;

import java.util.ArrayList;

/**
 * 天气数据类
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-12 7:22
 */
public class WeatherData implements SubjectS {

    private final ArrayList<ObserverS> observers;

    private float temperature;

    private float humidity;

    private float pressure;

    public WeatherData() {
        observers = new ArrayList();
    }

    @Override
    public void registerObserver(ObserverS o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(ObserverS o) {
        int index = observers.indexOf(o);
        if (index >= 0) {
            observers.remove(o);
        }
    }

    @Override
    public void notifyObservers() {
        for (int i = 0; i < observers.size(); i++) {
            ObserverS observerS = observers.get(i);
            observerS.update(temperature, humidity, pressure);
        }
    }

    /**
     * 改变数据时，通知观察者
     *
     * @param temperature 温度
     * @param humidity    湿度
     * @param pressure    气压
     */
    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        notifyObservers();
    }
}
