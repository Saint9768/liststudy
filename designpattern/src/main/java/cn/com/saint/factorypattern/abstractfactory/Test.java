package cn.com.saint.factorypattern.abstractfactory;

/**
 * 抽象工厂模式 --》 一个具体的工厂生产多种产品对象。
 * 优点：具体产品在应用层代码隔离，无需关心创建细节；将一系列产品族（可扩展）统一到一起创建。
 * 缺点：规定了所有可能被创建的产品集合，产品族中扩展新的产品困难，需要修改抽象工厂的接口。
 * <p>
 * 工厂方法模式利用继承，抽象工厂模式利用组合。
 * 工厂方法模式产生一个对象，抽象工厂模式产生一族对象。
 *
 * @author Saint
 * @createTime 2020-02-27 21:24
 */
public class Test {
    public static void main(String[] args) {
        AbstractFactory huaWeiFactory = new HuaWeiFactory();
        AbstractFactory appleFactory = new AppleFactory();
        huaWeiFactory.makePC();
        huaWeiFactory.makePhone();
        appleFactory.makePC();
        appleFactory.makePhone();

    }
}
