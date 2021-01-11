package cn.com.saint.observer.weather;

/**
 * 气象站测试类
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-12 7:37
 */
public class WeatherDataTest {
    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();
        CurrConditionDisplay conditionDisplay = new CurrConditionDisplay(weatherData);
        PressureDisplay pressureDisplay = new PressureDisplay(weatherData);
        weatherData.setMeasurements(80, 65, 30.5f);
        System.out.println("移除一个观察者后-------------");
        // 移除观察者
        weatherData.removeObserver(pressureDisplay);
        weatherData.setMeasurements(800, 650, 300.5f);
        System.out.println("添加一个观察者后------------------");
        // 新增观察者
        weatherData.registerObserver(pressureDisplay);
        weatherData.setMeasurements(66, 666, 60.5f);
    }
}
