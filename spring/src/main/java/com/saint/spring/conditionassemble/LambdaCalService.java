package com.saint.spring.conditionassemble;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-24 22:13
 */
@Profile("Java8")
@Service
public class LambdaCalService implements CalculateService {
    @Override
    public Integer sum(Integer... value) {
        Integer sum = Stream.of(value).reduce(0, Integer::sum);
        System.out.println("Java8 labmda : " + sum);
        return sum;
    }
}
