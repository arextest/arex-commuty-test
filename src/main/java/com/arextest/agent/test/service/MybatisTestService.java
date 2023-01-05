package com.arextest.agent.test.service;

import com.arextest.agent.test.entity.Mealrecomrestaurant;
import com.arextest.agent.test.mapper.MybatisPlusMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author yongwuhe
 * @date 2022/11/04
 */
@Component
public class MybatisTestService {
    @Autowired
    MybatisPlusMapper plusMapper;

    public String testMybatisQuery() {
        try {
            QueryWrapper<Mealrecomrestaurant> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(Mealrecomrestaurant::getDishName, "ravioli");
            List<Mealrecomrestaurant> mealrecomrestaurants = plusMapper.selectList(queryWrapper);
            return String.format("testMybatisQuery response: %s", mealrecomrestaurants.toString());
        } catch (Throwable ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String testMybatisInsert() {
        Mealrecomrestaurant mealrecomrestaurant = new Mealrecomrestaurant();
        mealrecomrestaurant.setRestaurantName("testMybatisPlus");
        mealrecomrestaurant.setDishName("testInsert");
        mealrecomrestaurant.setFlavorId(1);
        mealrecomrestaurant.setPrice(BigDecimal.valueOf(20));
        mealrecomrestaurant.setScore(BigDecimal.valueOf(4));
        int insert = plusMapper.insert(mealrecomrestaurant);
        return String.format("testMybatisInsert response: %s", insert);
    }

    public String testMybatisDelete() {
        prepareTestItems();
        QueryWrapper<Mealrecomrestaurant> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Mealrecomrestaurant::getRestaurantName, "FOOD");
        int delete = plusMapper.delete(queryWrapper);
        return String.format("testMybatisDelete response: %s", delete);
    }

    public String testMybatisUpdate() {
        prepareTestItems();
        LambdaUpdateWrapper<Mealrecomrestaurant> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Mealrecomrestaurant::getDishName, "rice").set(Mealrecomrestaurant::getScore, 10);
        int update = plusMapper.update(null, updateWrapper);
        return String.format("testMybatisUpdate response: %s", update);
    }

    public String testMybatisBatchUpdate() {
        prepareTestItems();
        LambdaUpdateWrapper<Mealrecomrestaurant> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Mealrecomrestaurant::getRestaurantName, "FOOD").set(Mealrecomrestaurant::getScore, 10);
        int update = plusMapper.update(null, updateWrapper);
        return String.format("testMybatisUpdate response: %s", update);
    }

    private void prepareTestItems() {
        List<Mealrecomrestaurant> items = queryTempTestItems();
        if (items.size() < 1) {
            insertTempTestItems();
        }
    }

    private List<Mealrecomrestaurant> queryTempTestItems() {
        QueryWrapper<Mealrecomrestaurant> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Mealrecomrestaurant::getRestaurantName, "FOOD");
        List<Mealrecomrestaurant> items = plusMapper.selectList(queryWrapper);
        return items;
    }

    private void insertTempTestItems() {
        Mealrecomrestaurant mealrecomrestaurant = new Mealrecomrestaurant("FOOD", "rice", 1, BigDecimal.valueOf(15), BigDecimal.valueOf(9));
        insertItem(mealrecomrestaurant);
        mealrecomrestaurant = new Mealrecomrestaurant("FOOD", "noodles", 2, BigDecimal.valueOf(35), BigDecimal.valueOf(8));
        insertItem(mealrecomrestaurant);
    }

    private int insertItem(Mealrecomrestaurant mealrecomrestaurant) {
        return plusMapper.insert(mealrecomrestaurant);
    }

    /**
     * create test data
     * @return
     */
    public List<Mealrecomrestaurant> getTempTestItems() {
        List<Mealrecomrestaurant> items = queryTempTestItems();
        if (items.size() < 1) {
            insertTempTestItems();
            return queryTempTestItems();
        }
        return items;
    }
}
