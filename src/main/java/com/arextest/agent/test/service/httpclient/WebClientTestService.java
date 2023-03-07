package com.arextest.agent.test.service.httpclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Component
@Slf4j
public class WebClientTestService extends HttpClientTestServiceBase{

    private static String asyncGetResponse, asyncPostResponse;

    public String asyncWebClientTest(String input) {
        asyncGetResponse = "";
        asyncPostResponse = "";

        WebClient webClient = WebClient.create();

        // get
        Mono<String> mono = webClient.get().uri(GET_URL).retrieve().bodyToMono(String.class);
        mono.subscribe(WebClientTestService::handleAsyncGetResponse);

        // post, with parameters
        LinkedMultiValueMap map = new LinkedMultiValueMap();
        map.add("userId", "1");
        map.add("title", "java");
        map.add("body", "spring");
        map.add("input", input);
        mono = webClient.post().uri(POST_URL).body(BodyInserters.fromFormData(map)).retrieve().bodyToMono(String.class);
        mono.subscribe(WebClientTestService::handleAsyncPostResponse);

        return  "AsyncGetResponse:" + asyncGetResponse + ";" + "\n" +
                "AsyncPostResponse:" + asyncPostResponse + ";" + "\n" +
                "After waite for some miniutes:" +"\n" +
                "AsyncGetResponse:" + mono.toString() + ";" + "\n" +
                "AsyncPostResponse:" + map.toString() + ";" + "\n";
    }

    private static void handleAsyncGetResponse(String result) {
        asyncGetResponse = result;
    }

    private static void handleAsyncPostResponse(String result) {
        asyncPostResponse = result;
    }
}
