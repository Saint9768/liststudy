package com.saint.spring.conditionassemble;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-24 22:12
 */
public interface CalculateService {

    /**
     * 求和
     *
     * @param value
     * @return
     */
    Integer sum(Integer... value);
}
