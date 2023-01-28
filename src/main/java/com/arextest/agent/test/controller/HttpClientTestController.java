package com.arextest.agent.test.controller;

import com.arextest.agent.test.entity.Request;
import com.arextest.agent.test.service.httpclient.*;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * @author yongwuhe
 * @date 2022/11/01
 */
@Controller
@RequestMapping(value = "/httpClientTest")
public class HttpClientTestController {

    @Autowired
    CommonClientTestService commonClientTestService;

    @Autowired
    RestTemplateTestService restTemplateTestService;

    @Autowired
    OkHttpTestService okHttpTestService;

    @Autowired
    ApacheHttpClientTestService apacheHttpClientTestService;

    @RequestMapping(value = "/commonClient")
    @ResponseBody
    public String commonClientTest(@RequestBody Request request) {
        String param = (request == null || StringUtil.isBlank(request.getInput())  ) ? "{\"userId\": 3, \"title\": \"Programmer\", \"body\":\"C++\"}" : request.getInput();
        return commonClientTestService.commonClientTest(param);
    }

    @RequestMapping(value = "/restTemplate")
    @ResponseBody
    public String restTemplate(@RequestBody Request request) throws InterruptedException {
        String param = (request == null || StringUtil.isBlank(request.getInput())  ) ? "Hi_Friend" : request.getInput();
        return restTemplateTestService.restTemplateTest(param);
    }

    @RequestMapping(value = "/okHttp")
    @ResponseBody
    public String okHttp(@RequestBody Request request) {
        String param = (request == null || StringUtil.isBlank(request.getInput())  ) ? "Hi_Friend" : request.getInput();
        return okHttpTestService.okHttpTest(param);
    }

    @RequestMapping(value = "/apacheClient")
    @ResponseBody
    public String apacheClient(@RequestBody Request request) throws IOException, ExecutionException, InterruptedException {
        String param = (request == null || StringUtil.isBlank(request.getInput())  ) ? "Hi_Friend" : request.getInput();
        return apacheHttpClientTestService.test(param);
    }
}
