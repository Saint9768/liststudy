package com.saint.mapper;

import com.saint.entity.Blog;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2020-09-22 20:56
 */
public interface BlogMapper {

    Blog selectBlog(Integer id);
}
