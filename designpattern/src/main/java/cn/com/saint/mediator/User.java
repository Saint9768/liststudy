package cn.com.saint.mediator;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2020-10-07 18:06
 */
public class User {
    private String name;

    public String getName() {
        return name;
    }

    public User(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void sendMessage(String message) {
        ChatRoom.showMessage(this, message);
    }
}
