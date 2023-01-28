package com.arextest.agent.test.service.redis;

import com.arextest.agent.test.config.RedisConfig;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

/**
 * @author daixq
 * @date 2022/12/07
 */
@Component
public class RedissionTestService {
    @Autowired
    RedisConfig redisConfig;

    RedissonClient redissonClient;

    @PostConstruct
    public void init() {
        Config config = new Config();
        config.useSingleServer().setAddress(String.format("redis://%s:%s", redisConfig.getHost(), redisConfig.getPort()))
                .setDatabase(1);
        redissonClient = Redisson.create(config);
    }

    public String testGetList(String parameterData) {
        RList<String> rList = redissonClient.getList(parameterData);
        return String.format("getList: %s", rList.add("bing"));
    }

    public String testGetLock(String parameterData) {
        RLock rLock = redissonClient.getLock(parameterData);
        return String.format("getLock: %s", rLock.getName());
    }

    public String testGetId() {
        String id = redissonClient.getId();
        return String.format("getId: %s", id);
    }

    public String testIsShuttingDown() {
        boolean isShuttingDown = redissonClient.isShuttingDown();
        return String.format("isShuttingDown: %s", isShuttingDown);
    }

    public String testIsShutdown() {
        boolean isShutdown = redissonClient.isShutdown();
        return String.format("isShutdown: %s", isShutdown);
    }

    public String testGetConfig() {
        Config config = redissonClient.getConfig();
        return String.format("config.getLockWatchdogTimeout: %s", config.getLockWatchdogTimeout());
    }

    public String testGetAtomicLong() {
        RAtomicLong rAtomicLong = redissonClient.getAtomicLong("testGetAtomicLong");
        return String.format("getAtomicLong: %s", rAtomicLong.incrementAndGet());
    }

    public String testGetAtomicDouble() {
        RAtomicDouble rAtomicDouble = redissonClient.getAtomicDouble("testGetAtomicDouble");
        return String.format("getAtomicLong: %s", rAtomicDouble.incrementAndGet());
    }

    public String testGetLongAdder() {
        RLongAdder rLongAdder = redissonClient.getLongAdder("testGetLongAdder");
        return String.format("getLongAdder: %s", rLongAdder.getName());
    }

    public String testGetDoubleAdder() {
        RDoubleAdder rDoubleAdder = redissonClient.getDoubleAdder("testDoubleAdder");
        return String.format("getDoubleAdder: %s", rDoubleAdder.getName());
    }

    public String testRenameException() {
        try {
            redissonClient.getKeys().rename("renameKey1", "renameKey2");
            return "rename ok";
        } catch (Exception e) {
            return "rename exception, message: " + e.getMessage();
        }
    }
}
