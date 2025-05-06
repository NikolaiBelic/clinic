package com.company.clinic.service.redis;

import java.util.Map;
import java.util.Set;

public interface RedisService {
    String NAME = "clinic_RedisService";

    public Map<String, Object> getMap(String key);
    public boolean containsKey(String key);

    public Map<String, Object> getAllKeysAndValues(String pattern);
    public Set<String> getKeys(String pattern);
    public void delete(String key);
}