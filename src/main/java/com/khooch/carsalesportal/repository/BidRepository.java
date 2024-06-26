package com.khooch.carsalesportal.repository;

import com.khooch.carsalesportal.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long> {

    @Query("SELECT b FROM Bid b WHERE b.car.id = :carId AND b.user.id = :userId")
    List<Bid> findByCarIdAndUserId(Long carId, Long userId);

    @Query("SELECT b FROM Bid b WHERE b.car.id = :carId ORDER BY b.bidAmount DESC")
    Optional<Bid> findHighestBidForCar(Long carId);

    Optional<Bid> findTopByCarIdOrderByBidAmountDesc(Long carId);
}
