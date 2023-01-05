package com.arextest.agent.test.controller;

import com.arextest.agent.test.service.jwt.JwtTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author daixq
 * @date 2022/11/11
 */
@Controller
@RequestMapping(value = "/jwtTest")
public class JwtTestController {

    @Autowired
    JwtTestService jwtTestService;

    @RequestMapping (value = "/jwt")
    @ResponseBody
    public String jwtTest() {
        return jwtTestService.testJwt();
    }
}
