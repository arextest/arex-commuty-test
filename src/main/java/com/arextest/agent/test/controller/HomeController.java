package com.arextest.agent.test.controller;

import org.springframework.boot.SpringBootVersion;
import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author daixq
 * @date 2022/11/29
 */
@Controller
public class HomeController {

    @RequestMapping
    @ResponseBody
    public String hello() {
        String sVersion = SpringVersion.getVersion();
        String bVersion = SpringBootVersion.getVersion();
        return String.format("Hello AREX agent test. Spring version: %s, Spring Boot version: %s", sVersion, bVersion) ;
    }
}
