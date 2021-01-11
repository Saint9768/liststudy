package cn.com.saint.observer.weather;

/**
 * 气象站观察者
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-11 21:40
 */
public interface ObserverS {

    /**
     * 当气象观测值改变时，主题会将这些状态值当做方法的参数，传送给观察者。
     *
     * @param temp     温度
     * @param humidity 湿度
     * @param pressure 气压
     */
    void update(float temp, float humidity, float pressure);

}
