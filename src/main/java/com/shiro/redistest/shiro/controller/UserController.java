package com.shiro.redistest.shiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @GetMapping("/login")
    String login() {
        return "login";
    }

    @RequestMapping("/index")
    String test() {
        return "test";
    }

    @GetMapping("/add")
    String add() {
        return "add";
    }

    @GetMapping("/update")
    String update() {
        return "update";
    }

    @ResponseBody
    @GetMapping("/admin")
    String admin() {
        return "欢迎您超级管理者!";
    }
}
