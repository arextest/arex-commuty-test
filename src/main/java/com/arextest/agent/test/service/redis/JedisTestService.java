package com.arextest.agent.test.service.redis;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.GetExParams;
import redis.clients.jedis.params.SetParams;

/**
 * @author yongwuhe
 * @date 2022/11/04
 * todo every redis api
 */
public class JedisTestService {

    private JedisPool jedisPool;

    private static volatile JedisTestService Instance = null;

    public static JedisTestService getInstance(String host, int port) {
        if (Instance == null) {
            synchronized (JedisTestService.class) {
                if (Instance == null) {
                    Instance = new JedisTestService();
                    Instance.jedisPool = new JedisPool(host, port);
                }
            }
        }
        return Instance;
    }

    private static final String TEST_REDIS_STRING_KEY = "testKeyString";
    private static final String TEST_REDIS_STRING_KEY2 = "testKeyString2";

    private static final String TEST_REDIS_STRING_VALUE = "testValueString";
    private static final String TEST_REDIS_STRING_VALUE2 = "testValueString2";

    private static final byte[] TEST_REDIS_BYTE_KEY = "testByteKey".getBytes(StandardCharsets.UTF_8);
    private static final byte[] TEST_REDIS_BYTE_KEY2 = "testByteKey2".getBytes(StandardCharsets.UTF_8);

    private static final byte[] TEST_REDIS_BYTE_VALUE = "testByteValue".getBytes(StandardCharsets.UTF_8);
    private static final byte[] TEST_REDIS_BYTE_VALUE2 = "testByteValue2".getBytes(StandardCharsets.UTF_8);

    public String testSet() {
        try (Jedis jedis = jedisPool.getResource()) {
            String stringSet = setStringKey1(jedis);
            String byteSet = setByteKey1(jedis);
            return String.format("stringSet: %s, byteSet: %s", stringSet, byteSet);
        }
    }

    public String testSetWithParams() {
        try (Jedis jedis = jedisPool.getResource()) {
            SetParams setParams = new SetParams();
            setParams.xx();

            setStringKey1(jedis);
            String stringSetWithParams = jedis.set(TEST_REDIS_STRING_KEY, TEST_REDIS_STRING_VALUE, setParams);

            setByteKey1(jedis);
            String byteSetWithParams = jedis.set(TEST_REDIS_BYTE_KEY, TEST_REDIS_BYTE_VALUE, setParams);
            return String.format("stringSetWithParams: %s, byteSetWithParams: %s", stringSetWithParams, byteSetWithParams);
        }
    }

    public String testGet() {
        try (Jedis jedis = jedisPool.getResource()) {
            setStringKey1(jedis);
            String stringGet = getStringKey1(jedis);

            setByteKey1(jedis);
            String byteGet = getByteKey1(jedis);
            return String.format("stringGet: %s, byteGet: %s", stringGet, byteGet);
        }
    }

    public String testGetEx() {
        try (Jedis jedis = jedisPool.getResource()) {
            setStringKey1(jedis);
            GetExParams getExParams = new GetExParams();
            getExParams.ex(1);
            String stringGetEx = jedis.getEx(TEST_REDIS_STRING_KEY, getExParams);

            setByteKey1(jedis);
            byte[] byteGetEx = jedis.getEx(TEST_REDIS_BYTE_KEY, getExParams);
            return String.format("stringGetEx: %s, byteGetEx: %s", stringGetEx, new String(byteGetEx, StandardCharsets.UTF_8));
        }
    }

    public String testGetDel() {
        try (Jedis jedis = jedisPool.getResource()) {
            setStringKey1(jedis);
            String stringGetDel = jedis.getDel(TEST_REDIS_STRING_KEY);
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("stringGetDel: %s", stringGetDel));

            setByteKey1(jedis);
            byte[] byteGetDel = jedis.getDel(TEST_REDIS_BYTE_KEY);
            sb.append(String.format("byteGetDel: %s", new String(byteGetDel, StandardCharsets.UTF_8)));
            return sb.toString();
        }
    }

    // It seems that the copy method only be supported by the redis with the version of 6.2.0 or above
    public String testCopy() {
        try (Jedis jedis = jedisPool.getResource()) {
            // copy string
            jedis.set(TEST_REDIS_STRING_KEY, TEST_REDIS_STRING_VALUE);
            boolean stringCopy = jedis.copy(TEST_REDIS_STRING_KEY, TEST_REDIS_STRING_KEY2, true);

            // copy byte
            jedis.set(TEST_REDIS_BYTE_KEY, TEST_REDIS_BYTE_VALUE);
            boolean byteCopy = jedis.copy(TEST_REDIS_BYTE_KEY, TEST_REDIS_BYTE_KEY2, true);
            return String.format("stringCopy: %s, byteCopy: %s", stringCopy, byteCopy);
        }
    }

    public String testDel() {
        try (Jedis jedis = jedisPool.getResource()) {
            setStringKey1(jedis);
            long stringDel = jedis.del(TEST_REDIS_STRING_KEY);

            setByteKey1(jedis);
            long byteDel = jedis.del(TEST_REDIS_BYTE_KEY);
            return String.format("stringDel: %s, byteDel: %s", stringDel, byteDel);
        }
    }

    public String testDelMultiKeys() {
        try (Jedis jedis = jedisPool.getResource()) {
            setStringKey1(jedis);
            setStringKey2(jedis);
            long stringDelMultiKeys = jedis.del(TEST_REDIS_STRING_KEY, TEST_REDIS_STRING_KEY2);

            setByteKey1(jedis);
            setByteKey2(jedis);
            long byteDelMultiKeys = jedis.del(TEST_REDIS_BYTE_KEY, TEST_REDIS_BYTE_KEY2);

            return String.format("stringDelMultiKeys: %s, byteDelMultiKeys: %s", stringDelMultiKeys, byteDelMultiKeys);
        }
    }

    public String testPing() {
        try (Jedis jedis = jedisPool.getResource()) {
            String pong = jedis.ping();
            return String.format("stringPing: %s", pong);
        }
    }

    public String testPingWithParameter() {
        try (Jedis jedis = jedisPool.getResource()) {
            byte[] bytePong = jedis.ping(TEST_REDIS_BYTE_KEY);
            return String.format("ping with parameter: %s", new String(bytePong, StandardCharsets.UTF_8));
        }
    }

    public String testExists() {
        try (Jedis jedis = jedisPool.getResource()) {
            setStringKey1(jedis);
            setStringKey2(jedis);
            long strNum = jedis.exists(TEST_REDIS_STRING_KEY, TEST_REDIS_STRING_KEY2);

            setByteKey1(jedis);
            setByteKey2(jedis);
            long byteNum = jedis.exists(TEST_REDIS_BYTE_KEY, TEST_REDIS_BYTE_KEY2);
            return String.format("stringKeyExists: %s, byteKeyExists: %s", strNum, byteNum);
        }
    }

    public String testUnlink() {
        try (Jedis jedis = jedisPool.getResource()) {
            setStringKey1(jedis);
            long strNum = jedis.unlink(TEST_REDIS_STRING_KEY);

            setByteKey1(jedis);
            long byteNum = jedis.unlink(TEST_REDIS_BYTE_KEY);

            return String.format("stringUnlink: %s, byteUnlink: %s", strNum, byteNum);
        }
    }

    public String testUnlinkMultiKeys() {
        try (Jedis jedis = jedisPool.getResource()) {
            setStringKey1(jedis);
            setStringKey2(jedis);
            long strNum = jedis.unlink(TEST_REDIS_STRING_KEY, TEST_REDIS_STRING_KEY2);

            setByteKey1(jedis);
            setByteKey2(jedis);
            long byteNum = jedis.unlink(TEST_REDIS_BYTE_KEY, TEST_REDIS_BYTE_KEY2);

            return String.format("stringUnlinkMultiKeys: %s, byteUnlinkMultiKeys: %s", strNum, byteNum);
        }
    }

    public String testType() {
        try (Jedis jedis = jedisPool.getResource()) {
            setStringKey1(jedis);
            String strType = jedis.type(TEST_REDIS_STRING_KEY);

            setByteKey1(jedis);
            String byteType = jedis.type(TEST_REDIS_BYTE_KEY);

            return String.format("strType: %s, byteType: %s", strType, byteType);
        }
    }

    public String testRename() {
        try (Jedis jedis = jedisPool.getResource()) {
            setStringKey1(jedis);
            String strNewName = jedis.rename(TEST_REDIS_STRING_KEY, TEST_REDIS_STRING_KEY + "_new");

            setByteKey1(jedis);
            String byteNewName = jedis.rename(TEST_REDIS_BYTE_KEY, "testByteKey_new".getBytes(StandardCharsets.UTF_8));

            return String.format("strNewName: %s, byteNewName: %s", strNewName, byteNewName);
        }
    }

    public String testRenameException() {
        try (Jedis jedis = jedisPool.getResource()) {
            String strNewName = jedis.rename(TEST_REDIS_STRING_KEY, TEST_REDIS_STRING_KEY + "_new");
            return String.format("strNewName: %s", strNewName);
        } catch (Exception e) {
            return "rename exception, message: " + e.getMessage();
        }
    }

    public String testRenameNx() {
        try (Jedis jedis = jedisPool.getResource()) {
            setStringKey1(jedis);
            long strRenameNum = jedis.renamenx(TEST_REDIS_STRING_KEY, TEST_REDIS_STRING_KEY + "_new");

            setByteKey1(jedis);
            long byteRenameNum = jedis.renamenx(TEST_REDIS_BYTE_KEY, "testByteKey_new".getBytes(StandardCharsets.UTF_8));

            return String.format("strRenameNum: %s, byteRenameNum: %s", strRenameNum, byteRenameNum);
        }
    }

    public String testExpire() {
        try (Jedis jedis = jedisPool.getResource()) {
            setStringKey1(jedis);
            long strExpire = jedis.expire(TEST_REDIS_STRING_KEY, 2);

            setByteKey1(jedis);
            long byteExpire = jedis.expire(TEST_REDIS_BYTE_KEY, 5);

            return String.format("strExpire: %s, byteExpire: %s", strExpire, byteExpire);
        }
    }

    public String testExpireTime() {
        try (Jedis jedis = jedisPool.getResource()) {
            setStringKey1(jedis);
            long strExpireTime = jedis.expireTime(TEST_REDIS_STRING_KEY);

            setByteKey1(jedis);
            long byteExpireTime = jedis.expireTime(TEST_REDIS_BYTE_KEY);

            return String.format("strExpireTime: %s, byteExpireTime: %s", strExpireTime, byteExpireTime);
        }
    }

    public String testAppend() {
        try (Jedis jedis = jedisPool.getResource()) {
            setStringKey1(jedis);
            long strAppend = jedis.append(TEST_REDIS_STRING_KEY, "_appendString");

            setByteKey1(jedis);
            long byteAppend = jedis.append(TEST_REDIS_BYTE_KEY, "_appendByte".getBytes());

            return String.format("strAppend: %s, byteAppend: %s", strAppend, byteAppend);
        }
    }

    public String testSubStr() {
        try (Jedis jedis = jedisPool.getResource()) {
            setStringKey1(jedis);
            String strSubStr = jedis.substr(TEST_REDIS_STRING_KEY, 1, 3);

            setByteKey1(jedis);
            byte[] byteSubStr = jedis.substr(TEST_REDIS_BYTE_KEY, 2, 5);

            return String.format("strSubStr: %s, byteSubStr: %s", strSubStr, new String(byteSubStr, StandardCharsets.UTF_8));
        }
    }

    public String testHSet() {
        try (Jedis jedis = jedisPool.getResource()) {
            long strHSet = jedis.hset("stringHash", "STRING_KEY", "testStringHset");
            Map<String, String> stringHash = createStringHash();
            long strHSetHash = jedis.hset("stringHash2", stringHash);

            long byteHSet = jedis.hset("byteHash".getBytes(), "BYTE_KEY".getBytes(), "testByteHset".getBytes());
            Map<byte[], byte[]> byteHash = createByteHash();
            long byteHSetHash = jedis.hset("byteHash2".getBytes(), byteHash);

            return String.format("strHSet: %s, strHSetHash: %s, byteHSet: %s, byteHSetHash: %s", strHSet, strHSetHash, byteHSet, byteHSetHash);
        }
    }

    public String testHGet() {
        try (Jedis jedis = jedisPool.getResource()) {
            setHash(jedis);

            String strHGet = jedis.hget("stringHash", "key1");

            byte[] byteHGet = jedis.hget("byteHash".getBytes(), "key2".getBytes());

            return String.format("strHGet: %s, byteHget: %s", strHGet, new String(byteHGet, StandardCharsets.UTF_8));
        }
    }

    public String testHSetNx() {
        try (Jedis jedis = jedisPool.getResource()) {
            setHash(jedis);

            long stringHSetNx = jedis.hsetnx("stringHash", "key1", "111");

            long byteHSetNx = jedis.hsetnx("byteHash".getBytes(), "key2".getBytes(), "222".getBytes());

            return String.format("stringHSetNx: %s, byteHSetNx: %s", stringHSetNx, byteHSetNx);
        }
    }

    public String testHMSet() {
        try (Jedis jedis = jedisPool.getResource()) {
            setHash(jedis);

            String stringHMSet = jedis.hmset("stringHash", new HashMap<String, String>() {{
                put("a", "b");
            }});

            String byteHMSet = jedis.hmset("byteHash".getBytes(), new HashMap<byte[], byte[]>() {{
                put("c".getBytes(), "d".getBytes());
            }});

            return String.format("stringHMSet: %s, byteHMSet: %s", stringHMSet, byteHMSet);
        }
    }

    public String testHMGet() {
        try (Jedis jedis = jedisPool.getResource()) {
            setHash(jedis);

            List<String> stringHMGet = jedis.hmget("stringHash", "key1", "key2");

            List<byte[]> byteHMGet = jedis.hmget("byteHash".getBytes(), "key1".getBytes(), "key2".getBytes());
            List<String> byteToStringList = byteHMGet.stream().map(byteValue -> new String(byteValue, StandardCharsets.UTF_8)).collect(Collectors.toList());

            return String.format("stringHMGet: %s, byteHMGet: %s", String.join(";", stringHMGet), String.join(";", byteToStringList));
        }
    }

    public String testHExists() {
        try (Jedis jedis = jedisPool.getResource()) {
            setHash(jedis);

            boolean stringHExists = jedis.hexists("stringHash", "key1");

            boolean byteHExists = jedis.hexists("byteHash".getBytes(), "key1".getBytes());

            return String.format("stringHExists: %s, byteHExists: %s", stringHExists, byteHExists);
        }
    }

    public String testHDel() {
        try (Jedis jedis = jedisPool.getResource()) {
            setHash(jedis);

            long stringHDel = jedis.hdel("stringHash", "key1");

            long byteHDel = jedis.hdel("byteHash".getBytes(), "key1".getBytes());

            return String.format("stringHDel: %s, byteHDel: %s", stringHDel, byteHDel);
        }
    }

    public String testHLen() {
        try (Jedis jedis = jedisPool.getResource()) {
            setHash(jedis);

            long stringHLen = jedis.hlen("stringHash");

            long byteHLen = jedis.hlen("byteHash".getBytes());

            return String.format("stringHLen: %s, byteHLen: %s", stringHLen, byteHLen);
        }
    }

    public String testHKeys() {
        try (Jedis jedis = jedisPool.getResource()) {
            setHash(jedis);

            Set<String> stringHKeys = jedis.hkeys("stringHash");

            Set<byte[]> byteHKeys = jedis.hkeys("byteHash".getBytes());
            Set<String> byteToStringSet = byteHKeys.stream().map(byteValue -> new String(byteValue, StandardCharsets.UTF_8)).collect(Collectors.toSet());

            return String.format("stringHKeys: %s, byteHKeys: %s", String.join(";", stringHKeys), String.join(";", byteToStringSet));
        }
    }

    public String testHVals() {
        try (Jedis jedis = jedisPool.getResource()) {
            setHash(jedis);

            List<String> stringHVals = jedis.hvals("stringHash");

            List<byte[]> byteHVals = jedis.hvals("byteHash".getBytes());
            List<String> byteToStringList = byteHVals.stream().map(byteValue -> new String(byteValue, StandardCharsets.UTF_8)).collect(Collectors.toList());

            return String.format("stringHVals: %s, byteHVals: %s", String.join(";", stringHVals), String.join(",", byteToStringList));
        }
    }

    public String testHGetAll() {
        try (Jedis jedis = jedisPool.getResource()) {
            setHash(jedis);

            Map<String, String> stringHGetAll = jedis.hgetAll("stringHash");

            Map<byte[], byte[]> byteHGetAll = jedis.hgetAll("byteHash".getBytes());
            Map<String, String> byteToStringMap = byteHGetAll.entrySet().stream().collect(
                    Collectors.toMap(
                            e -> new String(e.getKey(), StandardCharsets.UTF_8),
                            e -> new String(e.getValue(), StandardCharsets.UTF_8)
                    ));

            return String.format("stringHGetAll: %s, byteHGetAll: %s", stringHGetAll, byteToStringMap);
        }
    }

    public String testHRandField() {
        try (Jedis jedis = jedisPool.getResource()) {
            setHash(jedis);

            String stringHRandField = jedis.hrandfield("stringHash");

            byte[] byteHRandField = jedis.hrandfield("byteHash".getBytes());

            return String.format("stringHRandField: %s, byteHRandField: %s", stringHRandField, new String(byteHRandField, StandardCharsets.UTF_8));
        }
    }

    private void setHash(Jedis jedis) {
        Map<String, String> stringHash = createStringHash();
        jedis.hset("stringHash", stringHash);

        Map<byte[], byte[]> byteHash = createByteHash();
        jedis.hset("byteHash".getBytes(), byteHash);
    }

    private Map<String, String> createStringHash() {
        Map<String, String> stringHash = new HashMap<>();
        stringHash.put("key1", "string value1");
        stringHash.put("key2", "string value2");
        return stringHash;
    }

    private Map<byte[], byte[]> createByteHash() {
        Map<byte[], byte[]> byteHash = new HashMap<>();
        byteHash.put("key1".getBytes(), "byte value1".getBytes());
        byteHash.put("key2".getBytes(), "byte value2".getBytes());
        return byteHash;
    }

    private String setStringKey1(Jedis jedis) {
        return jedis.set(TEST_REDIS_STRING_KEY, TEST_REDIS_STRING_VALUE);
    }

    private String setStringKey2(Jedis jedis) {
        return jedis.set(TEST_REDIS_STRING_KEY2, TEST_REDIS_STRING_VALUE2);
    }

    private String setByteKey1(Jedis jedis) {
        return jedis.set(TEST_REDIS_BYTE_KEY, TEST_REDIS_BYTE_VALUE);
    }

    private String setByteKey2(Jedis jedis) {
        return jedis.set(TEST_REDIS_BYTE_KEY2, TEST_REDIS_BYTE_VALUE2);
    }

    private String getStringKey1(Jedis jedis) {
        return jedis.get(TEST_REDIS_STRING_KEY);
    }

    private String getByteKey1(Jedis jedis) {
        return new String(jedis.get(TEST_REDIS_BYTE_KEY), StandardCharsets.UTF_8);
    }
}
