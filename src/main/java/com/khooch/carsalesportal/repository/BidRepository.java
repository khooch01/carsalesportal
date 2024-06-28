package com.khooch.carsalesportal.repository;

import com.khooch.carsalesportal.entity.Bid;
import com.khooch.carsalesportal.entity.BidStatus;
import com.khooch.carsalesportal.entity.User;

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

    @Query("SELECT b FROM Bid b WHERE b.appointmentDate IS NOT NULL ORDER BY b.appointmentDate ASC")
    List<Bid> findAllWithAppointmentDatesOrdered();

    List<Bid> findByUserAndStatusName(User user, String statusName);
    List<Bid> findByUser(User user);
    Bid findByUserAndCarId(User user, Long carId);
    List<Bid> findByUserAndStatus(User user, BidStatus status);
    
}
