package cn.com.saint.factorypattern.factorymethod;

/**
 * 抽象工厂类
 * 生产 不同产品的工厂 的抽象类
 */
public interface AbstractFactory {
    Phone makePhone();
}
