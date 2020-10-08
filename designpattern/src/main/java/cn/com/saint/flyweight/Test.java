package cn.com.saint.flyweight;

/**
 * 享元模式
 *
 * @author Saint
 * @version 1.0
 * @createTime 2020-10-08 14:37
 */
public class Test {
    private static final String[] COLORS = {"Red", "Green", "Blue", "Write"};

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            Circle circle = (Circle) ShapeFactory.getCircle(getRandomColor());
            circle.setX(getRandom());
            circle.setY(getRandom());
            circle.draw();
        }
    }

    private static String getRandomColor() {
        return COLORS[(int) (Math.random() * COLORS.length)];
    }

    private static int getRandom() {
        return (int) (Math.random() * 100);
    }
}
