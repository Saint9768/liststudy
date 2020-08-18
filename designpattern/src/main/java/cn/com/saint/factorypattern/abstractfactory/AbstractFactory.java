package cn.com.saint.factorypattern.abstractfactory;

/**
 * 抽象工厂类
 * 里面包含两种不同类型的产品
 */
public interface AbstractFactory {
    Phone makePhone();

    PC makePC();
}
