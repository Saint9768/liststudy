package cn.com.saint.factorypattern.simplefactory;

/**
 * 简单工厂模式（只有一个抽象产品类）
 * 优点：实现对象的创建和对象的使用分离，将对象的创建交给专门的工厂负责。
 * 缺点：增加新的产品需要修改工厂类的判断逻辑，违背开闭原则。并且可扩展性不好。
 *
 * @author Saint
 * @createTime 2020-02-27 21:15
 */
public class Test {
    public static void main(String[] args) {
        //实例化工厂类
        PhoneFactory factory = new PhoneFactory();
        //实例化父类接口Phone.
        Phone huaweiPhone = factory.makePhone("HuaWeiPhone");
        //实例化具体手机类型，后面需要强制类型转换
        IPhone iPhone = (IPhone) factory.makePhone("IPhone");
    }
}
