package cn.com.saint.observer.weather;

import cn.com.saint.observer.Subject;

/**
 * 气象信息布告板
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-12 7:29
 */
public class CurrConditionDisplay implements ObserverS, DisplayElement {
    private float temperature;
    private float humidity;
    /**
     * 监控的主题
     */
    private final SubjectS weatherData;

    /**
     * 将观察者注册到主题中
     *
     * @param weatherData 主题
     */
    public CurrConditionDisplay(SubjectS weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public void display() {
        System.out.println("Current conditions:" + temperature + "F degrees and "
                + humidity + "% humidity");
    }

    @Override
    public void update(float temp, float humidity, float pressure) {
        this.temperature = temp;
        this.humidity = humidity;
        // 数据发生变更时打印数据
        display();
    }
}
