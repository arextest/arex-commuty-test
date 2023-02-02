package com.arextest.agent.test.controller.redis;

import com.arextest.agent.test.entity.Request;
import com.arextest.agent.test.service.redis.LettuceTestService;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.util.annotation.Nullable;

@Controller
@RequestMapping(value = "/lettuceTest")
public class LettuceTestController {
    @Autowired
    LettuceTestService lettuceTestService;


    @RequestMapping(value = "/renameException")
    @ResponseBody
    public String testRenameException(@Nullable @RequestBody Request request) {
        String param = (request == null || StringUtil.isBlank(request.getInput())  ) ? "hkey1" : request.getInput();
        return lettuceTestService.testRenameException(param);
    }
}
