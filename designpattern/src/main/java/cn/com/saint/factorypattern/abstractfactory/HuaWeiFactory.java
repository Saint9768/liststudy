package cn.com.saint.factorypattern.abstractfactory;

/**
 * 具体工厂类
 *
 * @author Saint
 * @createTime 2020-02-27 21:40
 */
public class HuaWeiFactory implements AbstractFactory {
    @Override
    public Phone makePhone() {
        return new HuaWeiPhone();
    }

    @Override
    public PC makePC() {
        return new MateBook();
    }
}
