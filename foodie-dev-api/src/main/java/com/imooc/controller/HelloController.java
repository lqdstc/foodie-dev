package com.imooc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@ApiIgnore
@RestController
public class HelloController {


    @GetMapping("/hello")
    public Object hello() {
        log.info("info:hello");
        log.debug("debug");
        log.error("error");
        return "hello word";

    }


    @GetMapping("/setSession")
    public Object setSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("userInfo", "new user");
        session.setMaxInactiveInterval(3600);
        session.getAttribute("userInfo");
        session.removeAttribute("userInfo");
        return "ok";
    }
}
