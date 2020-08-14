package com.saint.base.stream;

import java.util.Optional;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2020-08-14 7:27
 */
public class OptionalTest {

    public static Optional<Double> get(Double x) {
        //return Optional.ofNullable(x);
        return x == null ? Optional.empty() : Optional.of(x);
    }

    public static Optional<Double> squareRoot(Double x) {
        return x < 0 ? Optional.empty() : Optional.of(Math.sqrt(x));
    }

    public static void main(String[] args) {
        //使用flatMap实现链式调用
        Optional<Double> flatMap = OptionalTest.get(9.0).flatMap(OptionalTest::squareRoot);

        Optional<Double> flatMap2 = Optional.of(9.0).flatMap(OptionalTest::get).flatMap(OptionalTest::squareRoot);
        System.out.println(flatMap2.get());

    }
}
