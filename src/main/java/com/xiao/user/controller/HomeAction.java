package com.xiao.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Description TODO
 * @Author xiaoxuewang_vendor
 * @Date 2018/12/1 9:27
 */
@Controller
@Slf4j
public class HomeAction {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String helloHtml() {
        log.info("hello {}", "欢迎进入HTML页面");
        return "/index.html";
    }
}
