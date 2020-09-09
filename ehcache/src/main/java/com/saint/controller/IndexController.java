package com.saint.controller;

import com.saint.entity.User;
import com.saint.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2020-09-09 7:46
 */
@RestController
public class IndexController {

    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public User getUser(int id) {
        User user = userService.getById(id);
        System.out.println(user.toString());
        return user;
    }

    @RequestMapping("/delete")
    @CacheEvict(cacheNames = "user")
    public String deleteUser() {
        System.out.println("hello");
        return "hello";
    }
}
