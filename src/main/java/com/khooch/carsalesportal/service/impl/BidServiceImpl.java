package com.khooch.carsalesportal.service.impl;

import com.khooch.carsalesportal.entity.Bid;
import com.khooch.carsalesportal.repository.BidRepository;
import com.khooch.carsalesportal.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidServiceImpl implements BidService {

    private final BidRepository bidRepository;

    @Autowired
    public BidServiceImpl(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }

    @Override
    public Bid postBid(Bid bid, String username) {
        bid.setUsername(username);
        return bidRepository.save(bid);
    }

    @Override
    public List<Bid> findByUser(String username) {
        return bidRepository.findByUsername(username);
    }

    @Override
    public List<Bid> findAll() {
        return bidRepository.findAll();
    }

    @Override
    public void deleteBid(Long id) {
        bidRepository.deleteById(id);
    }

    @Override
    public List<Bid> getAllBids() {
        return bidRepository.findAll();
    }
}
