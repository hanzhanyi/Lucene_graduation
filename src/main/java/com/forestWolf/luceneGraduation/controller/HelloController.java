package com.forestWolf.luceneGraduation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hzy on 2018-1-3.
 */
@RestController
public class HelloController {
    @RequestMapping("hello")
    public String HelloWorld(){
        return "hello";
    }
}
