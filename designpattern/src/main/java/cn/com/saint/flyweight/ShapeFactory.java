package cn.com.saint.flyweight;

import java.util.HashMap;

/**
 * Shape工厂
 *
 * @author Saint
 * @version 1.0
 * @createTime 2020-10-08 14:35
 */
public class ShapeFactory {
    private static final HashMap<String, Shape> circleMap = new HashMap<>();

    public static Shape getCircle(String color) {
        Circle circle = (Circle) circleMap.get(color);
        if (circle == null) {
            circle = new Circle(color);
            circleMap.put(color, circle);
            System.out.println("Create Circle of color:" + color);
        }
        return circle;
    }
}
