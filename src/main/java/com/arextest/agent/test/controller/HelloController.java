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
    @GetMapping("/admin/hello")
    public String admin(){
        return "{\"response\":\"hello admin\"}";
    }
    @GetMapping("/user/hello")
    public String user(){
        return "{\"response\":\"hello user\"}";
    }
    @GetMapping("/db/hello")
    public String dba(){
        return "{\"response\":\"hello dba\"}";
    }
    @GetMapping("/hello")
    public String hello(){
        return "{\"response\":\"welcome\"}";
    }

    @RequestMapping(value = "/admin/hello")
    @ResponseBody
    public String adminPost(@RequestBody Request request){
        String param = (request == null || StringUtil.isBlank(request.getInput())) ? "admin" : request.getInput();
        return "{\"response\":\"hello "+ param +"\"}";
    }
    @RequestMapping(value = "/user/hello")
    @ResponseBody
    public String userPost(@RequestBody Request request){
        String param = (request == null || StringUtil.isBlank(request.getInput())) ? "user" : request.getInput();
        return "{\"response\":\"hello "+ param +"\"}";
    }
    @RequestMapping(value = "/db/hello")
    @ResponseBody
    public String dbaPost(@RequestBody Request request){
        String param = (request == null || StringUtil.isBlank(request.getInput())) ? "dba" : request.getInput();
        return "{\"response\":\"hello "+ param +"\"}";
    }
    @RequestMapping(value = "/hello")
    @ResponseBody
    public String helloPost(@RequestBody Request request){
        String param = (request == null || StringUtil.isBlank(request.getInput())) ? "welcome" : request.getInput();
        return "{\"response\":\"hello "+ param +"\"}";
    }
}
