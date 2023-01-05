package com.arextest.agent.test.controller;

import com.arextest.agent.test.service.netty.NettyTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author daixq
 * @date 2022/11/09
 */
@Controller
@RequestMapping(value = "/nettyTest")
public class NettyTestController {

    @Autowired
    private NettyTestService nettyTestService;

    @RequestMapping (value = "/nettyTest")
    @ResponseBody
    public String nettyTest() {
        return nettyTestService.nettyTest();
    }
}
