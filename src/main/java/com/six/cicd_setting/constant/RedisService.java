package com.six.cicd_setting.constant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public String redisString(String testKey, String testValue) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set(testKey, testValue);
        return operations.get(testKey);
    }
}
