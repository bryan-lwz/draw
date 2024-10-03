package com.pt.draw.controller;

import com.pt.draw.dto.DrawResultDTO;
import com.pt.draw.entity.PrizeEntity;
import com.pt.draw.entity.UserPrizeEntity;
import com.pt.draw.service.DrawService;
import com.pt.draw.service.RedisLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/draw")
public class DrawController {
    private static final String LOCK_KEY_PREFIX = "lock:draw:";
    @Autowired
    DrawService drawService;
    @Autowired
    RedisLockService redisLockService;
    @Autowired
    private RedisTemplate<String, String> template;

    @GetMapping("{userId}")
    public ResponseEntity<DrawResultDTO> draw(@PathVariable Long userId) {
        String lockKey = LOCK_KEY_PREFIX + userId;
        boolean lockAcquired = redisLockService.acquireLock(lockKey, 2);

        if (!lockAcquired) {
           return new ResponseEntity<>(new DrawResultDTO(), HttpStatus.BAD_REQUEST);
        }

        try {
            return drawService.draw(userId);
        } finally {
            redisLockService.releaseLock(lockKey);
        }
    }

    @GetMapping("history/{userId}")
    public ResponseEntity<List<UserPrizeEntity>> userPrizeHistory(@PathVariable Long userId) {
        return drawService.getUserPrizeHistory(userId);
    }

    @GetMapping("prize/all")
    public ResponseEntity<List<PrizeEntity>> getPrizeList() {
        return drawService.getAllPrizeList();
    }
}
