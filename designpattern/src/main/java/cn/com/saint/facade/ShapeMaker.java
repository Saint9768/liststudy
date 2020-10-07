package cn.com.saint.facade;

/**
 * 外观类
 *
 * @author Saint
 * @version 1.0
 * @createTime 2020-10-07 17:49
 */
public class ShapeMaker {
    private final Shape circle;
    private final Shape rectangle;
    private final Shape square;

    public ShapeMaker() {
        circle = new Circle();
        rectangle = new Rectangle();
        square = new Square();
    }

    public void drawCircle() {
        circle.draw();
    }

    public void drawRectangle() {
        rectangle.draw();
    }

    public void drawSquare() {
        square.draw();
    }
}
