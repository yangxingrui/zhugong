package com.zhugong.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/test")
public class ZhugongTestController {

    @GetMapping("/sayHello")
    public String sayHello() {
        return "hello";
    }

}
