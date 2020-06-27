package com.saint.spring.controller;

import com.saint.spring.annotation.AutoWired2;
import com.saint.spring.service.UserService;
import lombok.Data;

/**
 * @author Saint
 */
public class UserController {
    @AutoWired2
    private UserService userService;

    public UserController() {
    }

    public UserService getUserService() {
        return this.userService;
    }

    /**
     * 仅作为反射测试，自定义注解中没有使用
     * @param userService
     */
    /*public void setUserService(UserService userService) {
        this.userService = userService;
    }*/
}
