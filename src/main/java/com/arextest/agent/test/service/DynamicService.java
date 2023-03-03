package com.arextest.agent.test.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import com.arextest.agent.test.entity.Mealrecomrestaurant;
import com.arextest.agent.test.mapper.MybatisPlusMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;


@Service
public class DynamicService<T> {

    public DynamicService(){
        getRandomInt();
        getUuid();
        getCurrentTime();
    }

    @Autowired
    MybatisPlusMapper plusMapper;

    public String readFile(String fileName) {
        File file = new File(fileName);
        throw new RuntimeException(fileName + "FileNotFoundException 2");
    }

    public CompletableFuture<List<Mealrecomrestaurant>> getRestaurants() {
        return CompletableFuture.supplyAsync(
                () -> {
                    QueryWrapper<Mealrecomrestaurant> queryWrapper = new QueryWrapper<>();
                    queryWrapper.lambda().eq(Mealrecomrestaurant::getRestaurantName, "FOOD");
                    return plusMapper.selectList(queryWrapper);
                });
    }

    public Future<List<Mealrecomrestaurant>> getRestaurantsAsFuture() {
        return java.util.concurrent.Executors.newSingleThreadExecutor().submit(
                () -> {
                    QueryWrapper<Mealrecomrestaurant> queryWrapper = new QueryWrapper<>();
                    queryWrapper.lambda().eq(Mealrecomrestaurant::getRestaurantName, "FOOD");
                    return plusMapper.selectList(queryWrapper);
                });
    }

    public com.google.common.util.concurrent.ListenableFuture<List<Mealrecomrestaurant>> getRestaurantsAsListenableFuture() {
        return  MoreExecutors.listeningDecorator(java.util.concurrent.Executors.newCachedThreadPool()).submit(
                () -> {
                    QueryWrapper<Mealrecomrestaurant> queryWrapper = new QueryWrapper<>();
                    queryWrapper.lambda().eq(Mealrecomrestaurant::getRestaurantName, "FOOD");
                    return plusMapper.selectList(queryWrapper);
                });
        }

    public String getRandomInt() {
        Random rm = new Random();
        int n = rm.nextInt();
        return "Random number : " + String.valueOf(n);
    }

    public String getUuid() {
        String uuid = UUID.randomUUID().toString();
        return "uuid : " + String.valueOf(uuid);
    }

    public String getCurrentTime() {
        return "current time : " + String.valueOf(java.lang.System.currentTimeMillis());
    }

    public String optionTest(T t){
        return "T Int value is " + t;
    }
}
