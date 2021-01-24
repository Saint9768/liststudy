package com.saint.spring.conditionassemble;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-24 22:14
 */
@Profile("Java7")
@Service
public class InterationCalService implements CalculateService {
    @Override
    public Integer sum(Integer... value) {
        Integer sum = 0;
        for (Integer num : value) {
            sum += num;
        }
        System.out.println("Java7 迭代实现， sum: " + sum);
        return sum;
    }
}
