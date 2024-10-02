package com.pt.draw.controller;

import com.pt.draw.dto.DrawResultDTO;
import com.pt.draw.entity.UserPrizeEntity;
import com.pt.draw.service.DrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/draw")
public class DrawController {
    @Autowired
    DrawService drawService;

    @GetMapping("{userId}")
    public ResponseEntity<DrawResultDTO> draw (@PathVariable Long userId) {
        return drawService.draw(userId);
    }

    @GetMapping("history/{userId}")
    public ResponseEntity<List<UserPrizeEntity>> userPrizeHistory(@PathVariable Long userId) {
        return drawService.getUserPrizeHistory(userId);
    }
}
