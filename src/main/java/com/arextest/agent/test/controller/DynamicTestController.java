package com.arextest.agent.test.controller;

import com.arextest.agent.test.entity.Request;
import com.arextest.agent.test.service.DynamicService;
import java.io.IOException;

import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/dynamicTest")
public class DynamicTestController {
    @Autowired
    DynamicService dynamicService;

    @RequestMapping (value = "/testException")
    @ResponseBody
    public String testException(@RequestBody Request request) {
        String param = (request == null || StringUtil.isBlank(request.getInput())  ) ? "D:/test.txt" : request.getInput();
        try {
            return dynamicService.readFile(param);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
