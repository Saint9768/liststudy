package cn.com.saint.factorypattern.abstractfactory;

/**
 * 第二个具体产品类（MateBook)
 *
 * @author Saint
 * @createTime 2020-02-27 21:38
 */
public class MateBook implements PC {
    public MateBook() {
        this.make();
    }

    @Override
    public void make() {
        System.out.println("make huawei MateBook!");
    }
}
