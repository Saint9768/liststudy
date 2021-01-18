package com.saint.spring.configure.autoconfigure;

import com.saint.spring.configure.TestConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-18 7:58
 */
@Configuration
@Import(TestConfiguration.class)
public class TestAutoConfiguration {
}
