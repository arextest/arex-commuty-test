package com.arextest.agent.test.controller.redis;

import com.arextest.agent.test.service.redis.LettuceTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/lettuceTest")
public class LettuceTestController {
    @Autowired
    LettuceTestService lettuceTestService;


    @RequestMapping(value = "/renameException")
    @ResponseBody
    public String testRenameException() {
        return lettuceTestService.testRenameException();
    }
}
