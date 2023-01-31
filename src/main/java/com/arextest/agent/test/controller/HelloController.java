package com.arextest.agent.test.controller;

import com.arextest.agent.test.entity.Request;
import jodd.util.StringUtil;
import org.springframework.web.bind.annotation.*;

/**
 * @author daixq
 * @date 2023/01/12
 */
@RestController
public class HelloController {
    @RequestMapping(value = "/admin/hello")
    @ResponseBody
    public String adminPost(Request request){
        String param = (request == null || StringUtil.isBlank(request.getInput())) ? "admin" : request.getInput();
        return "{\"response\":\"hello "+ param +"\"}";
    }
    @RequestMapping(value = "/user/hello")
    @ResponseBody
    public String userPost(Request request){
        String param = (request == null || StringUtil.isBlank(request.getInput())) ? "user" : request.getInput();
        return "{\"response\":\"hello "+ param +"\"}";
    }
    @RequestMapping(value = "/db/hello")
    @ResponseBody
    public String dbaPost(Request request){
        String param = (request == null || StringUtil.isBlank(request.getInput())) ? "dba" : request.getInput();
        return "{\"response\":\"hello "+ param +"\"}";
    }
    @RequestMapping(value = "/hello")
    @ResponseBody
    public String helloPost(Request request){
        String param = (request == null || StringUtil.isBlank(request.getInput())) ? "welcome" : request.getInput();
        return "{\"response\":\"hello "+ param +"\"}";
    }
}
