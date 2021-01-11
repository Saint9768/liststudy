package cn.com.saint.observer.weather;

import java.sql.PreparedStatement;

/**
 * 压强布告板
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-12 7:33
 */
public class PressureDisplay implements ObserverS, DisplayElement {
    private float pressure;
    private final SubjectS weatherData;

    public PressureDisplay(SubjectS subjectS) {
        this.weatherData = subjectS;
        subjectS.registerObserver(this);
    }

    @Override
    public void display() {
        System.out.println("Pressure: " + pressure);
    }

    @Override
    public void update(float temp, float humidity, float pressure) {
        this.pressure = pressure;
        display();
    }
}
