package com.arextest.agent.test.controller;

import com.arextest.agent.test.service.DynamicService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/dynamicTest")
public class DynamicTestController {
    @Autowired
    DynamicService dynamicService;

    @RequestMapping (value = "/testException")
    @ResponseBody
    public String testException() {
        try {
            return dynamicService.readFile("D:/test.txt");
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
