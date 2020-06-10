package com.saint.netty.encoder.messagepack;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.msgpack.annotation.Message;

import java.io.Serializable;

/**
 * @author Saint
 * @createTime 2020-06-10 22:22
 */
@ToString
@Message
public class UserInfo implements Serializable {
    private String name;
    private int age;

    public UserInfo() {

    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setAge(final int age) {
        this.age = age;
    }

}
