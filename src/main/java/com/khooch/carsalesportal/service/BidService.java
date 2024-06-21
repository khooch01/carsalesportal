package com.khooch.carsalesportal.service;

import com.khooch.carsalesportal.entity.Bid;

import java.util.List;

public interface BidService {

    Bid postBid(Bid bid, String username);

    List<Bid> findByUser(String username);

    List<Bid> findAll();

    void deleteBid(Long id);

    List<Bid> getAllBids();
}
