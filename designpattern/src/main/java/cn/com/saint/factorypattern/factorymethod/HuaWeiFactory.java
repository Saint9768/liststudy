package cn.com.saint.factorypattern.factorymethod;

/**
 * 具体产品类(HuaWeiFactory)
 *
 * @author Saint
 * @createTime 2020-02-27 21:28
 */
public class HuaWeiFactory implements AbstractFactory {
    @Override
    public Phone makePhone() {
        return new HuaWeiPhone();
    }
}
