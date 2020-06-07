package com.saint.spring.controller;

import com.saint.spring.annotation.AutoWired2;
import com.saint.spring.service.UserService;
import lombok.Data;

/**
 * @author Saint
 */
@Data
public class UserController {
    @AutoWired2
    private UserService userService;

    public UserController() {
    }

    public UserService getUserService() {
        return this.userService;
    }
}
