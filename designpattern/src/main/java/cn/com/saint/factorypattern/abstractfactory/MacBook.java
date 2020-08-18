package cn.com.saint.factorypattern.abstractfactory;

/**
 * 第二个具体产品类（MAC）
 *
 * @author Saint
 * @createTime 2020-02-27 21:37
 */
public class MacBook implements PC {
    public MacBook() {
        this.make();
    }

    @Override
    public void make() {
        System.out.println("make MAC!");
    }
}
