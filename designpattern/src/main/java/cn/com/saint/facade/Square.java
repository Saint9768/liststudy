package cn.com.saint.facade;

/**
 * 具体产品类
 *
 * @author Saint
 * @version 1.0
 * @createTime 2020-10-07 17:48
 */
public class Square implements Shape {
    @Override
    public void draw() {
        System.out.println("Square::draw()");
    }
}
