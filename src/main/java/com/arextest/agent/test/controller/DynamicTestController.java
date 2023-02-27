package com.arextest.agent.test.controller;

import com.arextest.agent.test.entity.Mealrecomrestaurant;
import com.arextest.agent.test.entity.Request;
import com.arextest.agent.test.mapper.MybatisPlusMapper;
import com.arextest.agent.test.service.DynamicService;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.util.annotation.Nullable;


@Controller
@Slf4j
@RequestMapping(value = "/dynamicTest")
public class DynamicTestController {
    @Autowired
    MybatisPlusMapper plusMapper;
    @Autowired
    DynamicService dynamicService;

    @RequestMapping (value = "/testException")
    @ResponseBody
    public String testException(@Nullable @RequestBody Request request) {
        String param = (request == null || StringUtil.isBlank(request.getInput())  ) ? "D:/test.txt" : request.getInput();
        try {
            return dynamicService.readFile(param);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping (value = "/testReturnCompletableFuture")
    @ResponseBody
    public String testReturnCompletableFuture() {
        CompletableFuture<List<Mealrecomrestaurant>> future = dynamicService.getRestaurants();
        future.whenComplete(((restaurants, throwable) -> {
            if (throwable != null) {
                log.error("testReturnCompletableFuture", throwable);
            }
        }));
        String restaurantNumber = "query failed";
        try {
            restaurantNumber = String.valueOf(future.get().size());
        } catch (Exception ex) {
            log.error("future.get", ex);
        }
        return "{\"Restaurant number\":\""+ restaurantNumber +"\"}";
    }
}
