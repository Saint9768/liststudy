package cn.com.saint.factorypattern.simplefactory;

/**
 * 具体工厂类
 * 根据具体产品的类型返回对应的方法。
 *
 * @author Saint
 * @createTime 2020-02-27 21:12
 */
public class PhoneFactory {
    public Phone makePhone(String phoneType) {
        if (phoneType.equalsIgnoreCase("HuaWeiPhone")) {
            return new HuaWeiPhone();
        } else if (phoneType.equalsIgnoreCase("IPhone")) {
            return new IPhone();
        }
        return null;
    }
}
