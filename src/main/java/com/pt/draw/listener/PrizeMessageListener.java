package com.pt.draw.listener;

import com.pt.draw.entity.UserEntity;
import com.pt.draw.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RocketMQMessageListener(topic = "topic-prize", consumerGroup = "draw-cg")
public class PrizeMessageListener {
    @Autowired
    private UserRepository userRepository;

    public void onMessage(@Payload UserEntity user) {
        if (user == null) return;

        user.setAvailableDrawCount(user.getAvailableDrawCount() - 1);
        userRepository.save(user);
        log.info("Available Draw Count Deducted for user {}", user.getUserName());
    }
}
