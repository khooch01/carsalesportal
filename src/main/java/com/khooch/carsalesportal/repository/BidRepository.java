package com.khooch.carsalesportal.repository;

import com.khooch.carsalesportal.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findByUsername(String username);
}
