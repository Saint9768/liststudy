package com.saint.autoconfigure;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-29 14:33
 */
public class DefaultFormatter implements Formatter {
    @Override
    public String format(Object object) {
        return String.valueOf(object);
    }
}
