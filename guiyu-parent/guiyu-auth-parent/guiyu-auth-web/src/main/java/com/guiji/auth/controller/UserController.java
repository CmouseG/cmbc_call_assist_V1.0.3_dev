package com.guiji.auth.controller;

import org.springframework.web.bind.annotation.*;

/**
 * Created by ty on 2018/10/22.
 */
@RestController
public class UserController {
    @RequestMapping(value = "/test" ,method = RequestMethod.GET)
    public String test() {
        return "hello,你好";
    }
}
