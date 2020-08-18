package cn.com.saint.strategypattern;

/**
 * 策略模式
 * 定义了一组算法，将每个算法都封装起来，并且使它们之间可以互换。
 * 优点：算法可以自由切换，扩展性好，增加一个策略只需要实现接口即可。
 * 缺点：策略类数量增多， 复用性小。所有策略类都需要对外暴露
 * 使用场景：多个类只有算法或行为上稍有不同的场景。算法可以自由切换。
 * 应用实例：出行方式，每一种出行方式都是一种策略。Java AWT中的LayoutManager(布局管理器)。
 *
 * @author Saint
 * @createTime 2020-03-01 8:12
 */
public class Strategy {

    public static void main(String[] args) {
        System.out.print("用户选择了GIF格式： ");
        ImageSave saver = Typechooser.getSaver("GIF");
        saver.save();

        System.out.print("用户选择了JPE格式： ");
        saver = Typechooser.getSaver("JPE");
        saver.save();

        System.out.print("用户选择了PNG格式： ");
        saver = Typechooser.getSaver("PNG");
        saver.save();

    }

}

/**
 * Context上下文
 */
class Typechooser {
    public static ImageSave getSaver(String type) {
        if (type.equalsIgnoreCase("GIF")) {
            return new GIFSaver();
        } else if (type.equalsIgnoreCase("JPE")) {
            return new JPESaver();
        } else if (type.equalsIgnoreCase("PNG")) {
            return new PNGSaver();
        } else {
            return null;
        }
    }
}

/**
 * 具体策略角色
 */
class GIFSaver implements ImageSave {
    @Override
    public void save() {
        System.out.println("将图片格式保存为GIF格式");
    }
}

class JPESaver implements ImageSave {
    @Override
    public void save() {
        System.out.println("将图片格式保存为JPE格式");
    }
}

class PNGSaver implements ImageSave {
    @Override
    public void save() {
        System.out.println("将图片格式保存为PNG格式");
    }
}
