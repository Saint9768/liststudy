package cn.com.saint.factorypattern.factorymethod;

/**
 * 具体产品类(AppleFactory)
 *
 * @author Saint
 * @createTime 2020-02-27 21:29
 */
public class AppleFactory implements AbstractFactory {
    @Override
    public Phone makePhone() {
        return new IPhone();
    }
}
