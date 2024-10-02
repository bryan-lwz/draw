package com.pt.draw.service.impl;

import com.pt.draw.dto.DrawResultDTO;
import com.pt.draw.entity.PrizeEntity;
import com.pt.draw.entity.UserEntity;
import com.pt.draw.entity.UserPrizeEntity;
import com.pt.draw.repository.PrizeRepository;
import com.pt.draw.repository.UserPrizeRepository;
import com.pt.draw.repository.UserRepository;
import com.pt.draw.service.DrawService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DrawServiceImpl implements DrawService {

    @Autowired
    PrizeRepository prizeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserPrizeRepository userPrizeRepository;

    @Transactional
    public ResponseEntity<DrawResultDTO> draw(Long userId) {
        var user = userRepository.findById(userId);
        // invalid user
        if (user.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        // no available draw count
        if (user.get().getAvailableDrawCount() < 1) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        // draw prize
        PrizeEntity drawnPrize = drawPrize();
        if (drawnPrize != null) {
            // save user
            UserEntity drawnUser = user.get();
            drawnUser.setDrawCount(user.get().getDrawCount() + 1);
            drawnUser.setAvailableDrawCount(user.get().getAvailableDrawCount() - 1);
            userRepository.save(drawnUser);

            UserPrizeEntity userPrize = new UserPrizeEntity();
            userPrize.setUserId(drawnUser.getUserId());
            userPrize.setPrizeId(drawnPrize.getId());
            log.info(userPrize.toString());
            userPrizeRepository.save(userPrize);

            drawnPrize.setQuantity(drawnPrize.getQuantity() - 1);
            prizeRepository.save(drawnPrize);

            DrawResultDTO drawResult = DrawResultDTO.builder().userId(userId).userName(drawnUser.getUserName())
                    .prizeId(drawnPrize.getId()).prizeName(drawnPrize.getPrizeName()).build();
            return new ResponseEntity<>(drawResult, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    PrizeEntity drawPrize() {
        List<PrizeEntity> prizes = prizeRepository.findAll();

        double totalProbability = 0;
        List<PrizeEntity> selectablePrizes = new ArrayList<>();

        for (PrizeEntity prize : prizes) {
            if (prize.getQuantity() > 0) {
                totalProbability += prize.getHitProbability();
                selectablePrizes.add(prize);
            }
        }

        if (selectablePrizes.isEmpty()) return null;

        Random random = new Random();
        double randomValue = random.nextDouble() * totalProbability;

        for (PrizeEntity prize : selectablePrizes) {
            randomValue -= prize.getHitProbability();
            if (randomValue <= 0) {
                prize.setQuantity(prize.getQuantity() - 1);
                prizeRepository.save(prize);
                return prize; // Return the selected prize
            }
        }

        return null;
    }

    public ResponseEntity<List<UserPrizeEntity>> getUserPrizeHistory(Long userId) {
        var user = userRepository.findById(userId);
        if (user.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        var userPrizeList = userPrizeRepository.findByUserId(userId);
        if (userPrizeList.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(userPrizeList, HttpStatus.OK);
    }

}