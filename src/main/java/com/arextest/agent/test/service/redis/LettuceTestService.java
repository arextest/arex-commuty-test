package com.arextest.agent.test.service.redis;

import com.arextest.agent.test.config.RedisConfig;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import io.lettuce.core.api.sync.RedisCommands;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class LettuceTestService {
    RedisCommands<String, String> syncCommands;
    RedisAsyncCommands<String, String> asyncCommands;

    RedisReactiveCommands<String, String> reactiveCommands;

    @Autowired
    RedisConfig redisConfig;

    @PostConstruct
    public void init() {
        RedisURI uri = RedisURI.builder().withHost(redisConfig.getHost()).withPort(redisConfig.getPort()).build();
        RedisClient redisClient = RedisClient.create(uri);
        try (StatefulRedisConnection<String, String> connection = redisClient.connect()) {
            syncCommands = connection.sync();
            asyncCommands = connection.async();
            reactiveCommands = connection.reactive();
        }
    }


    public String testRenameException() {
        try {
            Flux<String> flux = reactiveCommands.hkeys("hkey1");
            return "flux result: " + flux.blockFirst();
        } catch (Exception e) {
            return "rename exception, message: " + e.getMessage();
        }
    }
}
