package com.pt.draw.service;

import com.pt.draw.dto.DrawResultDTO;
import com.pt.draw.entity.UserPrizeEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DrawService {
    ResponseEntity<DrawResultDTO> draw(Long userId);

    ResponseEntity<List<UserPrizeEntity>> getUserPrizeHistory(Long userId);
}
