package cn.com.saint.observer;

/**
 * 观察者实体类
 *
 * @author Saint
 * @version 1.0
 * @createTime 2020-10-08 13:32
 */
public class BinaryObserver extends Observer {

    public BinaryObserver(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        System.out.println("Binary String :" + Integer.toBinaryString(subject.getState()));
    }
}
