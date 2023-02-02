package com.arextest.agent.test.controller;

import com.arextest.agent.test.entity.Request;
import jodd.util.StringUtil;
import org.springframework.web.bind.annotation.*;
import reactor.util.annotation.Nullable;

/**
 * @author daixq
 * @date 2023/01/12
 */
@RestController
public class HelloController {
    @RequestMapping(value = "/admin/hello")
    public String adminPost(@Nullable @RequestBody Request request){
        String param = (request == null || StringUtil.isBlank(request.getInput())) ? "admin" : request.getInput();
        return "{\"response\":\"hello "+ param +"\"}";
    }
    @RequestMapping(value = "/user/hello")
    public String userPost(@Nullable @RequestBody Request request){
        String param = (request == null || StringUtil.isBlank(request.getInput())) ? "user" : request.getInput();
        return "{\"response\":\"hello "+ param +"\"}";
    }
    @RequestMapping(value = "/db/hello")
    public String dbaPost(@Nullable @RequestBody Request request){
        String param = (request == null || StringUtil.isBlank(request.getInput())) ? "dba" : request.getInput();
        return "{\"response\":\"hello "+ param +"\"}";
    }
    @RequestMapping(value = "/hello")
    public String helloPost(@Nullable @RequestBody Request request){
        String param = (request == null || StringUtil.isBlank(request.getInput())) ? "welcome" : request.getInput();
        return "{\"response\":\"hello "+ param +"\"}";
    }
}
