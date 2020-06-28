package com.saint.spring.model;

import lombok.ToString;

/**
 * @author Saint
 * @createTime 2020-06-27 21:00
 */
@ToString
public class Book {
    private String name;
    private Double price;

    public Book() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
