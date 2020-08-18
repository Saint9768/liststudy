package cn.com.saint.factorypattern.factorymethod;

/**
 * 工厂方法模式
 * 优点：用户只需要关心所需产品对应的工厂，无需关注细节；假如新产品复合开闭原则，可扩展性强。
 * 缺点：类的个数容易特别多，增加复杂度。
 * 使用场景：创建对象需要大量重复的代码；客户端不依赖于产品类实例如何被创建。父类通过子类来指定创建哪个对象。
 *
 * @author Saint
 * @createTime 2020-02-27 21:19
 */
public class Test {
    public static void main(String[] args) {
        AbstractFactory huaWeiFactory = new HuaWeiFactory();
        AbstractFactory iphoneFactory = new AppleFactory();
        //华为具体产品工厂生产手机
        huaWeiFactory.makePhone();
        //苹果具体产品工厂生产手机
        iphoneFactory.makePhone();
    }
}
