package cn.com.saint.factorypattern.simplefactory;

/**
 * 具体产品类（huaweiPhone）
 *
 * @author Saint
 * @createTime 2020-02-27 21:10
 */
public class HuaWeiPhone implements Phone {
    public HuaWeiPhone() {
        this.make();
    }

    @Override
    public void make() {
        System.out.println("make HuaWeiPhone!");
    }
}
