package com.khooch.carsalesportal.service;

import com.khooch.carsalesportal.dto.BidDto;
import com.khooch.carsalesportal.entity.Bid;
import com.khooch.carsalesportal.entity.BidStatus;
import com.khooch.carsalesportal.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BidService {
    Bid save(BidDto bidDto);
    Bid findById(Long id);
    List<Bid> findAll();
    void deleteById(Long id);
    List<Bid> getAllBids();
    void approveBid(Long bidId);
    void denyBid(Long bidId);
    Bid getBidById(Long id);
    void updateBidStatus(Long bidId, BidStatus newStatus);
    BigDecimal getHighestBidPriceForCar(Long carId);
    Optional<Bid> findHighestBidForCar(Long carId);
    void save(Bid bid);
    List<Bid> findBidsByCarIdAndUserId(Long id, Long id2);
    void rejectBid(Long id);
    List<Bid> findApprovedBidsByUser(User user);
}
