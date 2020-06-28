package com.saint.spring;

import com.saint.spring.model.Book;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {ApplicationTests.class, Application.class})
@RunWith(SpringRunner.class)
class ApplicationTests {

    @Test
    public void test1() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Book book = context.getBean(Book.class);
        System.out.println(book);
    }

}
