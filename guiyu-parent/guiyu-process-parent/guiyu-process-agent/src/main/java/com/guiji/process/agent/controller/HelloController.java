package com.guiji.process.agent.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ty on 2018/12/12.
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String sayHello() {
        return "hello";
    }
}
