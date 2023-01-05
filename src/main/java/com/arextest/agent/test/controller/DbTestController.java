package com.arextest.agent.test.controller;

import com.arextest.agent.test.entity.Mealrecomrestaurant;
import com.arextest.agent.test.service.MybatisTestService;

import com.arextest.agent.test.service.hibernate.HibernateTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yongwuhe
 * @date 2022/11/05
 * to use mysql or h2, configure it in application.properties. by default, use mysql.
 */
@Controller
@RequestMapping(value = "/dbTest")
public class DbTestController {
    @Autowired
    MybatisTestService mybatisTestService;

    @Autowired
    HibernateTestService hibernateTestService;

    @RequestMapping (value = "/mybatis/query")
    @ResponseBody
    public String mybatisQueryTest() {
        return mybatisTestService.testMybatisQuery();
    }

    @RequestMapping  (value = "/mybatis/insert")
    @ResponseBody
    public String mybatisInsertTest() {
        return mybatisTestService.testMybatisInsert();
    }

    @RequestMapping  (value = "/mybatis/delete")
    @ResponseBody
    public String mybatisDeleteTest() {
        return mybatisTestService.testMybatisDelete();
    }

    @RequestMapping  (value = "/mybatis/update")
    @ResponseBody
    public String mybatisUpdateTest() {
        return mybatisTestService.testMybatisUpdate();
    }

    @RequestMapping  (value = "/mybatis/batchupdate")
    @ResponseBody
    public String mybatisBatchUpdateTest() {
        return mybatisTestService.testMybatisBatchUpdate();
    }

    @RequestMapping (value = "/hibernate/save")
    @ResponseBody
    public String hibernateSaveTest() {
        return hibernateTestService.testHibernateSave();
    }

    @RequestMapping (value = "/hibernate/saveAll")
    @ResponseBody
    public String hibernateSaveAllTest() {
        return hibernateTestService.testHibernateSaveAll();
    }

    @RequestMapping (value = "/hibernate/delete")
    @ResponseBody
    public String hibernateDeleteTest() {
        return hibernateTestService.testHibernateDelete();
    }

    @RequestMapping (value = "/hibernate/findAll")
    @ResponseBody
    public String hibernateFindAllTest() {
        return  hibernateTestService.testHibernateFindAll();
    }

    @RequestMapping (value = "/hibernate/findAllWithExample")
    @ResponseBody
    public String hibernateFindAllWithExampleTest() {
        return hibernateTestService.testHibernateFindAllWithExample();
    }

    @RequestMapping (value = "/hibernate/findById")
    @ResponseBody
    public String hibernateFindByIdTest() {
        return hibernateTestService.testHibernateFindById();
    }
}