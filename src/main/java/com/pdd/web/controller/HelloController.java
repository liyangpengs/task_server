package com.pdd.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author:liyangpeng
 * @date:2018/10/10 9:44
 */
@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }


}
