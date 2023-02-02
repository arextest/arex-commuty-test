package com.arextest.agent.test.controller.redis;

import com.arextest.agent.test.config.RedisConfig;
import com.arextest.agent.test.entity.Request;
import com.arextest.agent.test.service.redis.JedisTestService;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.PostConstruct;
import reactor.util.annotation.Nullable;
/**
 * @author yongwuhe
 * @date 2022/10/20
 * the redis configuration can be found in application.properties
 */
@Controller
@RequestMapping(value = "/redisTest")
public class RedisTestController {
    private JedisTestService jedisTestService;

    @Autowired
    RedisConfig redisConfig;

    @PostConstruct
    public void init() {
        jedisTestService = JedisTestService.getInstance(redisConfig.getHost(), redisConfig.getPort());
    }

    @RequestMapping(value = "/jedis/set")
    @ResponseBody
    public String testRedisSet(@Nullable @RequestBody Request request) {
        String param = (request == null || StringUtil.isBlank(request.getInput())  ) ? "" : request.getInput();
        return jedisTestService.testSet(param);
    }

    @RequestMapping(value = "/jedis/setWithParams")
    @ResponseBody
    public String setWithSetParams() {
        return jedisTestService.testSetWithParams();
    }

    @RequestMapping(value = "/jedis/get")
    @ResponseBody
    public String testRedisGet() {
        return jedisTestService.testGet();
    }

    @RequestMapping(value = "/jedis/getEx")
    @ResponseBody
    public String testRedisGetEx() {
        return jedisTestService.testGetEx();
    }

    @RequestMapping(value = "/jedis/getDel")
    @ResponseBody
    public String testRedisGetDel() {
        return jedisTestService.testGetDel();
    }

    @RequestMapping(value = "/jedis/copy")
    @ResponseBody
    public String testRedisCopy() {
        return jedisTestService.testCopy();
    }

    @RequestMapping(value = "/jedis/del")
    @ResponseBody
    public String testRedisDel() {
        return jedisTestService.testDel();
    }

    @RequestMapping(value = "/jedis/delMultiKeys")
    @ResponseBody
    public String testRedisDel2() {
        return jedisTestService.testDelMultiKeys();
    }

    @RequestMapping(value = "/jedis/ping")
    @ResponseBody
    public String testRedisPing() {
        return jedisTestService.testPing();
    }

    @RequestMapping(value = "/jedis/pingWithParam")
    @ResponseBody
    public String testRedisPingWithParam() {
        return jedisTestService.testPingWithParameter();
    }

    @RequestMapping(value = "/jedis/exists")
    @ResponseBody
    public String testExists() {
        return jedisTestService.testExists();
    }

    @RequestMapping(value = "/jedis/unlink")
    @ResponseBody
    public String testUnlink() {
        return jedisTestService.testUnlink();
    }

    @RequestMapping(value = "/jedis/unlinkMultiKeys")
    @ResponseBody
    public String testUnlinkMultiKeys() {
        return jedisTestService.testUnlinkMultiKeys();
    }

    @RequestMapping(value = "/jedis/type")
    @ResponseBody
    public String testType() {
        return jedisTestService.testType();
    }

    @RequestMapping(value = "/jedis/rename")
    @ResponseBody
    public String testRename() {
        return jedisTestService.testRename();
    }

    @RequestMapping(value = "/jedis/renameException")
    @ResponseBody
    public String testRenameException() {
        return jedisTestService.testRenameException();
    }

    @RequestMapping(value = "/jedis/renameNx")
    @ResponseBody
    public String testRenameNx() {
        return jedisTestService.testRenameNx();
    }

    @RequestMapping(value = "/jedis/expire")
    @ResponseBody
    public String testExpire() {
        return jedisTestService.testExpire();
    }

    @RequestMapping(value = "/jedis/expireTime")
    @ResponseBody
    public String testExpireTime() {
        return jedisTestService.testExpireTime();
    }

    @RequestMapping(value = "/jedis/append")
    @ResponseBody
    public String testAppend() {
        return jedisTestService.testAppend();
    }

    @RequestMapping(value = "/jedis/substr")
    @ResponseBody
    public String testSubStr() {
        return jedisTestService.testSubStr();
    }

    @RequestMapping(value = "/jedis/hset")
    @ResponseBody
    public String testHSet() {
        return jedisTestService.testHSet();
    }

    @RequestMapping(value = "/jedis/hget")
    @ResponseBody
    public String testHGet() {
        return jedisTestService.testHGet();
    }

    @RequestMapping(value = "/jedis/hsetnx")
    @ResponseBody
    public String testHSetNx() {
        return jedisTestService.testHSetNx();
    }

    @RequestMapping(value = "/jedis/hmset")
    @ResponseBody
    public String testHMSet() {
        return jedisTestService.testHMSet();
    }

    @RequestMapping(value = "/jedis/hmget")
    @ResponseBody
    public String testHMGet() {
        return jedisTestService.testHMGet();
    }

    @RequestMapping(value = "/jedis/hexists")
    @ResponseBody
    public String testHExists() {
        return jedisTestService.testHExists();
    }

    @RequestMapping(value = "/jedis/hdel")
    @ResponseBody
    public String testHDel() {
        return jedisTestService.testHDel();
    }

    @RequestMapping(value = "/jedis/hlen")
    @ResponseBody
    public String testHlen() {
        return jedisTestService.testHLen();
    }

    @RequestMapping(value = "/jedis/hkeys")
    @ResponseBody
    public String testHKeys() {
        return jedisTestService.testHKeys();
    }

    @RequestMapping(value = "/jedis/hvals")
    @ResponseBody
    public String testHVals() {
        return jedisTestService.testHVals();
    }

    @RequestMapping(value = "/jedis/hgetAll")
    @ResponseBody
    public String testHGetAll() {
        return jedisTestService.testHGetAll();
    }

    @RequestMapping(value = "/jedis/hrandfield")
    @ResponseBody
    public String testHRandField() {
        return jedisTestService.testHRandField();
    }
}
