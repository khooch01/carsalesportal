package com.khooch.carsalesportal.repository;

import com.khooch.carsalesportal.entity.BidStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BidStatusRepository extends JpaRepository<BidStatus, Long> {
    Optional<BidStatus> findByName(String name);
}
