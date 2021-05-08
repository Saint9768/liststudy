package com.saint.spring.test;

import org.apache.catalina.core.ApplicationContext;
import org.apache.naming.factory.BeanFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-03-16 7:31
 */
public class Dog implements ApplicationContextAware, BeanFactoryAware {

    private String name;

    private ApplicationContext applicationContext;

    private BeanFactory beanFactory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    public void setBeanFactory(org.springframework.beans.factory.BeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void setApplicationContext(org.springframework.context.ApplicationContext applicationContext) throws BeansException {

    }
}
