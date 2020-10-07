package cn.com.saint.decorator;

/**
 * 具体产品类
 *
 * @author Saint
 * @version 1.0
 * @createTime 2020-10-07 17:48
 */
public class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("Rectangle::draw()");
    }
}
