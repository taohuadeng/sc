package com.tbc.elf.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 这个controller测试使用
 */
@Controller
@RequestMapping("/test/*")
public class TestController {

    @RequestMapping("/success")
    public String success() {
        return "success";
    }
}
