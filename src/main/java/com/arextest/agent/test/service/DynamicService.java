package com.arextest.agent.test.service;

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import com.arextest.agent.test.entity.Mealrecomrestaurant;
import com.arextest.agent.test.mapper.MybatisPlusMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DynamicService {
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
}
