package com.saint.spring.autoassemble;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-24 21:56
 */
@Controller
public class HelloController {

    @RequestMapping("/")
    @ResponseBody
    public String helloWorld() {
        return "Hello World";
    }
}
