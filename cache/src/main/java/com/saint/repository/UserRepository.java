package com.saint.repository;

import com.saint.entity.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2020-09-09 7:40
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findById(int id);
}
