package cn.com.saint.flyweight;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2020-10-08 14:32
 */
public class Circle implements Shape {

    private final String color;
    private int x;
    private int y;

    public Circle(String color) {
        this.color = color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void draw() {
        System.out.println("Circle: Draw()[Color:" + color + ",x: " + x + ",y: " + y + "]");
    }
}
