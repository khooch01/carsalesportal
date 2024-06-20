package com.khooch.carsalesportal.service;

import com.khooch.carsalesportal.entity.Bid;

public interface BidService {
    void postBid(Bid bid, String username);

    Object findByUser(String name);
}
