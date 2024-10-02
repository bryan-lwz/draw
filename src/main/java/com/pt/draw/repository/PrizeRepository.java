package com.pt.draw.repository;

import com.pt.draw.entity.PrizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrizeRepository extends JpaRepository<PrizeEntity, Long> {
}
