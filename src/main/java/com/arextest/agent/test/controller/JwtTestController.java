package com.arextest.agent.test.controller;

import com.arextest.agent.test.entity.Request;
import com.arextest.agent.test.service.jwt.JwtTestService;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.util.annotation.Nullable;
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
    public String jwtTest(@Nullable @RequestBody Request request) {
        String param = (request == null || StringUtil.isBlank(request.getInput())  ) ? "credential" : request.getInput();
        return jwtTestService.testJwt(param);
    }
}
