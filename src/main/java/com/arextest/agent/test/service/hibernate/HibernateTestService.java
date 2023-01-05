package com.arextest.agent.test.service.hibernate;

import com.arextest.agent.test.entity.User;
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
        User user = new User();
        user.setName("Lily");
        user.setAge(18);
        User result = hibernateRepository.save(user);
        return String.format("testHibernateSave response: %s", result.toString());
    }

    public String testHibernateDelete() {
        List<User> users = prepareTestItems();
        int beforeDel = users.size();
        User user = users.get(0);
        hibernateRepository.delete(user);
        return String.format("size before delete: %s, size after delete: %s, deleted user: %s", beforeDel, beforeDel - 1, user.toString());
    }

    public String testHibernateFindById() {
        List<User> users = prepareTestItems();
        Optional<User> user = hibernateRepository.findById(users.get(0).getId());
        return String.format("testHibernateFindById response: %s", user.toString());
    }

    public String testHibernateSaveAll() {
        List<User> users = getTestItems();
        List<User> result = hibernateRepository.saveAll(users);
        return String.format("testHibernateSave response: %s", result.toString());
    }

    public String testHibernateFindAll() {
        prepareTestItems();
        List<User> result = hibernateRepository.findAll();
        return String.format("testHibernateFindAll response: %s", result.toString());
    }

    public String testHibernateFindAllWithExample() {
        prepareTestItems();
        User user = new User();
        user.setName("Jack");
        user.setAge(20);
        List<User> result = hibernateRepository.findAll(Example.of(user));
        return String.format("testHibernateFindAll response: %s", result.toString());
    }

    private List<User> prepareTestItems() {
        List<User> users = getTestItems();
        return hibernateRepository.saveAll(users);
    }

    public List<User> getTestItems() {
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setName("Kite");
        user.setAge(19);
        users.add(user);
        User user2 = new User();
        user2.setName("Jack");
        user2.setAge(20);
        users.add(user2);
        return users;
    }
}
