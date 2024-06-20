package com.khooch.carsalesportal.service.impl;

import com.khooch.carsalesportal.entity.Bid;
import com.khooch.carsalesportal.repository.BidRepository;
import com.khooch.carsalesportal.service.BidService;
import com.khooch.carsalesportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BidServiceImpl implements BidService {

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private UserService userService;

    @Override
    public void postBid(Bid bid, String username) {
        bid.setUser(userService.findByUsername(username));
        bidRepository.save(bid);
    }

    @Override
    public Object findByUser(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByUser'");
    }
}
