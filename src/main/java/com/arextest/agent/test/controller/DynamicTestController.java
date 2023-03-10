package com.arextest.agent.test.controller;

import com.arextest.agent.test.entity.Mealrecomrestaurant;
import com.arextest.agent.test.entity.Request;
import com.arextest.agent.test.mapper.MybatisPlusMapper;
import com.arextest.agent.test.service.DynamicService;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.util.annotation.Nullable;
import com.google.common.util.concurrent.FutureCallback;

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

    @RequestMapping (value = "/testRandomInt")
    @ResponseBody
    public String testRandomInt(@Nullable @RequestBody Request request) {
        try {
            return dynamicService.getRandomInt();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping (value = "/testUuid")
    @ResponseBody
    public String testUuid(@Nullable @RequestBody Request request) {
        try {
            return dynamicService.getUuid();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping (value = "/testCurentTime")
    @ResponseBody
    public String testCurentTime(@Nullable @RequestBody Request request) {
        try {
            return dynamicService.getCurrentTime();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping (value = "/testOptionClass")
    @ResponseBody
    public String testOptionClass(@Nullable @RequestBody Request request) {
        try {
            Integer value = 300;
            return dynamicService.optionTest(value);
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

    @RequestMapping (value = "/testReturnFuture")
    @ResponseBody
    public String testReturnFuture() {
        Future<List<Mealrecomrestaurant>> future = dynamicService.getRestaurantsAsFuture();

        String restaurantNumber = "query failed";
        while (!future.isDone()){
            log.info("future.get", " Doing...");
        };

        try {
            restaurantNumber = String.valueOf(future.get().size());
        } catch (Exception ex) {
            log.error("future.get", ex);
        }

        return "{\"Restaurant number\":\""+ restaurantNumber +"\"}";
    }

    @RequestMapping (value = "/testReturnListenableFuture")
    @ResponseBody
    public String testReturnListenableFuture() {
        com.google.common.util.concurrent.ListenableFuture<List<Mealrecomrestaurant>> listenableFuture
                = dynamicService.getRestaurantsAsListenableFuture();

        String restaurantNumber = "query failed";

        while (!listenableFuture.isDone()){
            log.info("future.get", " Doing...");
        };

        try {
            restaurantNumber = String.valueOf(listenableFuture.get().size());
        } catch (InterruptedException e) {
            log.error("future.get InterruptedException", e);
        } catch (ExecutionException e) {
            log.error("future.get ExecutionException", e);
        }

        return "{\"Restaurant number\":\""+ restaurantNumber +"\"}";
    }
}
