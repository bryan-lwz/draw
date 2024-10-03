package com.pt.draw.service;

public interface RedisLockService {
    boolean acquireLock(String lockKey, long timeout);

    void releaseLock(String lockKey);
}
