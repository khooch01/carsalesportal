package com.khooch.carsalesportal.service.impl;

import com.khooch.carsalesportal.entity.BidStatus;
import com.khooch.carsalesportal.repository.BidStatusRepository;
import com.khooch.carsalesportal.service.BidStatusService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BidStatusServiceImpl implements BidStatusService {

    private final BidStatusRepository bidStatusRepository;

    public BidStatusServiceImpl(BidStatusRepository bidStatusRepository) {
        this.bidStatusRepository = bidStatusRepository;
    }

    @Override
    @Transactional
    public BidStatus findOrCreateByName(String name) {
        Optional<BidStatus> optionalBidStatus = bidStatusRepository.findByName(name);
        return optionalBidStatus.orElseGet(() -> bidStatusRepository.save(new BidStatus(name)));
    }
}
