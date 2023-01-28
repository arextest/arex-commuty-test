package com.arextest.agent.test.controller.redis;

import com.arextest.agent.test.entity.Request;
import com.arextest.agent.test.service.redis.RedissionTestService;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author daixq
 * @date 2022/12/07
 */
@Controller
@RequestMapping(value = "/redissionTest")
public class RedissionTestController {
    @Autowired
    RedissionTestService redissionTestService;

    @RequestMapping(value = "/getList")
    @ResponseBody
    public String testGetList(@RequestBody Request request) {
        String param = (request == null || StringUtil.isBlank(request.getInput())  ) ? "my-list" : request.getInput();
        return redissionTestService.testGetList(param);
    }

    @RequestMapping(value = "/getLock")
    @ResponseBody
    public String testGetLock(@RequestBody Request request) {
        String param = (request == null || StringUtil.isBlank(request.getInput())  ) ? "my-lock" : request.getInput();
        return redissionTestService.testGetLock(param);
    }

    @RequestMapping(value = "/getId")
    @ResponseBody
    public String testGetId() {
        return redissionTestService.testGetId();
    }

    @RequestMapping(value = "/isShuttingDown")
    @ResponseBody
    public String testIsShuttingDown() {
        return redissionTestService.testIsShuttingDown();
    }

    @RequestMapping(value = "/isShutdown")
    @ResponseBody
    public String testIsShutdown() {
        return redissionTestService.testIsShutdown();
    }

    @RequestMapping(value = "/getConfig")
    @ResponseBody
    public String testGetConfig() {
        return redissionTestService.testGetConfig();
    }

    @RequestMapping(value = "/getAtomicLong")
    @ResponseBody
    public String testGetAtomicLong() {
        return redissionTestService.testGetAtomicLong();
    }

    @RequestMapping(value = "/getAtomicDouble")
    @ResponseBody
    public String testGetAtomicDouble() {
        return redissionTestService.testGetAtomicDouble();
    }

    @RequestMapping(value = "/getLongAdder")
    @ResponseBody
    public String testGetLongAdder() {
        return redissionTestService.testGetLongAdder();
    }

    @RequestMapping(value = "/getDoubleAdder")
    @ResponseBody
    public String testGetDoubleAdder() {
        return redissionTestService.testGetDoubleAdder();
    }


    @RequestMapping(value = "/renameException")
    @ResponseBody
    public String testRenameException() {
        return redissionTestService.testRenameException();
    }

}
