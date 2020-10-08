package cn.com.saint.observer;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2020-10-08 13:32
 */
public class HexaObserver extends Observer {

    public HexaObserver(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        System.out.println("Binary String :" + Integer.toHexString(subject.getState()));
    }
}
