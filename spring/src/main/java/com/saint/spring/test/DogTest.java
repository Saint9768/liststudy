package com.saint.spring.test;

import org.apache.naming.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-03-16 7:35
 */
public class DogTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("test.xml");
        Dog bean = applicationContext.getBean(Dog.class);
        BeanFactory beanFactory = bean.getBeanFactory();

    }
}
