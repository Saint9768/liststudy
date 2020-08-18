package cn.com.saint.factorypattern.simplefactory;

/**
 * 具体产品类（iPhone）
 *
 * @author Saint
 * @createTime 2020-02-27 21:10
 */
public class IPhone implements Phone {
    public IPhone() {
        this.make();
    }

    @Override
    public void make() {
        System.out.println("make IPhone!");
    }
}
