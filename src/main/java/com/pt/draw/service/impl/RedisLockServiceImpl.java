package com.pt.draw.service.impl;

import com.pt.draw.service.RedisLockService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RedisLockServiceImpl implements RedisLockService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public boolean acquireLock(String lockKey, long timeout) {
        Boolean success = redisTemplate.opsForValue().setIfAbsent(lockKey, "locked", timeout, TimeUnit.SECONDS);
        return success != null && success;
    }

    public void releaseLock(String lockKey) {
        redisTemplate.delete(lockKey);
    }
}
