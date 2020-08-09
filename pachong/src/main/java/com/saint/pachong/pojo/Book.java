package com.saint.pachong.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2020-08-09 10:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private String title;
    private String price;
    private String img;
}
