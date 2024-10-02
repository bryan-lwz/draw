package com.pt.draw.repository;

import com.pt.draw.entity.UserPrizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPrizeRepository extends JpaRepository<UserPrizeEntity, Long> {
    List<UserPrizeEntity> findByUserId(Long userId);
}
