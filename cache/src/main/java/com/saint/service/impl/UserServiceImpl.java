package com.saint.service.impl;

import com.saint.entity.User;
import com.saint.repository.UserRepository;
import com.saint.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2020-09-09 7:41
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Cacheable(value = "user")
    public User getById(int id) {
        User user = userRepository.findById(id);
        return user;
    }
}
