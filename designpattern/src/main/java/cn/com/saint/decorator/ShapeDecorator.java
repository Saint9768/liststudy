package cn.com.saint.decorator;

/**
 * 抽象装饰类
 *
 * @author Saint
 * @version 1.0
 * @createTime 2020-10-07 22:27
 */
public class ShapeDecorator implements Shape {
    protected Shape decoratedShape;

    public ShapeDecorator(Shape decoratedShape) {
        this.decoratedShape = decoratedShape;
    }

    @Override
    public void draw() {
        decoratedShape.draw();
    }
}
