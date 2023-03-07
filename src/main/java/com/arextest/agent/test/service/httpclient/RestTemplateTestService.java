package com.arextest.agent.test.service.httpclient;

import com.arextest.agent.test.entity.HttpMethodEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author daixq
 * @date 2022/11/04
 */
@Component
@Slf4j
public class RestTemplateTestService extends HttpClientTestServiceBase{

    private static String asyncGetResponse, asyncPostResponse;

    public String restTemplateTest(String parameterData) {
        String getResponse = restTemplate(HttpMethodEnum.GET, parameterData);
        String postResponse = restTemplate(HttpMethodEnum.POST, parameterData);
        return String.format("restTemplate get response: %s, restTemplate post response: %s", getResponse, postResponse);
    }

    private String restTemplate(HttpMethodEnum type, String input) {
        RestTemplate restTemplate = new RestTemplate();
        String response = "";
        if (type.equals(HttpMethodEnum.GET)) {
            response = restTemplate.getForObject(GET_URL, String.class);
        }

        if (type.equals(HttpMethodEnum.POST)){
            Map<String, Object> map = new HashMap<>();
            map.put("userId", 2);
            map.put("title", "php");
            map.put("body", "web");
            map.put("input", input);
            response = restTemplate.postForObject(POST_URL, map, String.class);
        }

        return response;
    }

}
