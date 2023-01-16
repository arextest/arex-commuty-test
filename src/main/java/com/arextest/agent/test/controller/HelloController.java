package com.arextest.agent.test.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return "{\"response\":\"Hello, " + authentication.getName() + "!\"}";
    }
}