package cn.com.saint.factorypattern.abstractfactory;

/**
 * 具体工厂类
 *
 * @author Saint
 * @createTime 2020-02-27 21:42
 */
public class AppleFactory implements AbstractFactory {
    @Override
    public Phone makePhone() {
        return new IPhone();
    }

    @Override
    public PC makePC() {
        return new MacBook();
    }
}
