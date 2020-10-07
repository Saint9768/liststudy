package cn.com.saint.facade;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2020-10-07 17:52
 */
public class FacadePatternDemo {
    public static void main(String[] args) {
        ShapeMaker shapeMaker = new ShapeMaker();
        shapeMaker.drawCircle();
        shapeMaker.drawRectangle();
        shapeMaker.drawSquare();
    }
}
