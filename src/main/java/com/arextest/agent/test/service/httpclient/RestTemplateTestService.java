package com.arextest.agent.test.service.httpclient;

import com.arextest.agent.test.entity.HttpMethodEnum;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.HashMap;
import java.util.Map;

/**
 * @author daixq
 * @date 2022/11/04
 */
@Component
public class RestTemplateTestService extends HttpClientTestServiceBase{

    private static String asyncGetResponse, asyncPostResponse;

    public String restTemplateTest() throws InterruptedException {
        asyncRestTemplate();
        String getResponse = restTemplate(HttpMethodEnum.GET);
        String postResponse = restTemplate(HttpMethodEnum.POST);
        Thread.sleep(1000); // wait async operation finish
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("restTemplate get response: %s, restTemplate post response: %s", getResponse, postResponse));
        sb.append(String.format("WebClient async get response: %s, WebClient async post response: %s", asyncGetResponse, asyncPostResponse));
        return sb.toString();
    }

    private String restTemplate(HttpMethodEnum type) {
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
            response = restTemplate.postForObject(POST_URL, map, String.class);
        }

        return response;
    }

    /**
     * Async invoke. AsyncRestTemplate is deprecated, instead use the WebClient
     * @return
     */
    private void asyncRestTemplate() {
        asyncGetResponse = "";
        WebClient webClient = WebClient.create();

        // get
        Mono<String> mono = webClient.get().uri(GET_URL).retrieve().bodyToMono(String.class);
        mono.subscribe(RestTemplateTestService::handleAsyncGetResponse);

        // post, with parameters
        LinkedMultiValueMap map = new LinkedMultiValueMap();
        map.add("userId", "1");
        map.add("title", "java");
        map.add("body", "spring");
        mono = webClient.post().uri(POST_URL).body(BodyInserters.fromFormData(map)).retrieve().bodyToMono(String.class);
        mono.subscribe(RestTemplateTestService::handleAsyncPostResponse);
    }

    private static void handleAsyncGetResponse(String result) {
        asyncGetResponse = result;
    }

    private static void handleAsyncPostResponse(String result) {
        asyncPostResponse = result;
    }
}
