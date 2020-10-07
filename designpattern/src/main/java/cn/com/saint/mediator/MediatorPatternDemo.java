package cn.com.saint.mediator;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2020-10-07 18:08
 */
public class MediatorPatternDemo {
    public static void main(String[] args) {
        // 使用User对象来完成他们之间的通信
        User robert = new User("Robert!");
        User john = new User("John!");
        john.sendMessage("Hi! Robert!");
        robert.sendMessage("Hello! John");
    }
}
