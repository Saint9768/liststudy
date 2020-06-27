package com.saint.base.exception;

/**
 * 自定义异常
 *
 * @author Saint
 * @createTime 2020-06-27 9:13
 */
public class MyException extends Exception {
    public MyException() {
    }

    /**
     * 带参的构造函数
     *
     * @param message 表示异常提示
     */
    public MyException(String message) {
        super(message);
    }

}
