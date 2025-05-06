package com.company.clinic.service.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service(RedisService.NAME)
public class RedisServiceBean implements RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void save(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }


    public void save(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, timeout, unit);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void saveMap(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    public void saveMap(String key, Map<String, Object> map , long timeout, TimeUnit unit) {
        redisTemplate.opsForHash().putAll(key, map);
        redisTemplate.expire(key, timeout, unit);
    }

    public Map<String, Object> getMap(String key) {

        Map<Object, Object> retrievedMap = redisTemplate.opsForHash().entries(key);
        Map<String, Object> resultMap = new HashMap<>();

        for (Map.Entry<Object, Object> entry : retrievedMap.entrySet()) {
            if (entry.getKey() instanceof String) {
                resultMap.put((String) entry.getKey(), entry.getValue());
            }
        }

        return resultMap;
    }

    public Object getMapValue(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    public void deleteMap(String key) {
        redisTemplate.delete(key);
    }

    public void deleteMapValue(String key, String hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    public boolean containsKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public Long getExpiration(String key) {
        return redisTemplate.getExpire(key);
    }

    public Set<String> getKeys(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);

        if (keys.equals(null)) {
            return Collections.emptySet();
        }

        return keys;
    }

    public Map<String, Object> getAllKeysAndValues(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);

        if (keys == null) {
            return Collections.emptyMap();
        }

        Map<String, Object> result = new HashMap<>();

        for (String key : keys) {
            Object value = redisTemplate.opsForValue().get(key);
            result.put(key, value);
        }

        return result;
    }
}