package cn.com.saint.mediator;

import java.util.Date;

/**
 * 调停者类(中介类)
 *
 * @author Saint
 * @version 1.0
 * @createTime 2020-10-07 18:05
 */
public class ChatRoom {
    public static void showMessage(User user, String message) {
        System.out.println(new Date().toString() + "[" + user.getName() + "]:" + message);
    }
}
