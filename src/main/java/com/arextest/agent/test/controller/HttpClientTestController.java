package com.arextest.agent.test.controller;

import com.arextest.agent.test.service.httpclient.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public String commonClientTest() {
        return commonClientTestService.commonClientTest();
    }

    @RequestMapping(value = "/restTemplate")
    @ResponseBody
    public String restTemplate() throws InterruptedException {
        return restTemplateTestService.restTemplateTest();
    }

    @RequestMapping(value = "/okHttp")
    @ResponseBody
    public String okHttp() {
        return okHttpTestService.okHttpTest();
    }

    @RequestMapping(value = "/apacheClient")
    @ResponseBody
    public String apacheClient() throws IOException, ExecutionException, InterruptedException {
        return apacheHttpClientTestService.test();
    }
}
