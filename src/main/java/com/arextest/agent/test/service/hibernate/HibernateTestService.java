package com.arextest.agent.test.service.hibernate;

import com.arextest.agent.test.entity.TestUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author daixq
 * @date 2022/11/07
 */
@Component
public class HibernateTestService {
    @Autowired
    HibernateRepository hibernateRepository;

    public String testHibernateSave() {
        TestUser testUser = new TestUser();
        testUser.setName("Lily");
        testUser.setAge(18);
        TestUser result = hibernateRepository.save(testUser);
        return String.format("testHibernateSave response: %s", result.toString());
    }

    public String testHibernateDelete() {
        List<TestUser> testUsers = prepareTestItems();
        int beforeDel = testUsers.size();
        TestUser testUser = testUsers.get(0);
        hibernateRepository.delete(testUser);
        return String.format("size before delete: %s, size after delete: %s, deleted user: %s", beforeDel, beforeDel - 1, testUser.toString());
    }

    public String testHibernateFindById(Integer userId) {
        List<TestUser> testUsers = prepareTestItems();
        Optional<TestUser> user = hibernateRepository.findById(testUsers.get(userId).getId());
        return String.format("testHibernateFindById response: %s", user.toString());
    }

    public String testHibernateSaveAll() {
        List<TestUser> testUsers = getTestItems();
        List<TestUser> result = hibernateRepository.saveAll(testUsers);
        return String.format("testHibernateSave response: %s", result.toString());
    }

    public String testHibernateFindAll() {
        prepareTestItems();
        List<TestUser> result = hibernateRepository.findAll();
        return String.format("testHibernateFindAll response: %s", result.toString());
    }

    public String testHibernateFindAllWithExample() {
        prepareTestItems();
        TestUser testUser = new TestUser();
        testUser.setName("Jack");
        testUser.setAge(20);
        List<TestUser> result = hibernateRepository.findAll(Example.of(testUser));
        return String.format("testHibernateFindAll response: %s", result.toString());
    }

    private List<TestUser> prepareTestItems() {
        List<TestUser> testUsers = getTestItems();
        return hibernateRepository.saveAll(testUsers);
    }

    public List<TestUser> getTestItems() {
        List<TestUser> testUsers = new ArrayList<>();
        TestUser testUser = new TestUser();
        testUser.setName("Kite");
        testUser.setAge(19);
        testUsers.add(testUser);
        TestUser testUser2 = new TestUser();
        testUser2.setName("Jack");
        testUser2.setAge(20);
        testUsers.add(testUser2);
        return testUsers;
    }
}
