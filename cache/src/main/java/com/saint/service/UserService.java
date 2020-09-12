package com.saint.service;

import com.saint.entity.User;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2020-09-09 7:35
 */
public interface UserService {

    /**
     * 根据Id获取用户
     *
     * @param id
     * @return
     */
    User getById(int id);
}
