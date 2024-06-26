package com.khooch.carsalesportal.configuration;

import com.khooch.carsalesportal.entity.BidStatus;
import com.khooch.carsalesportal.repository.BidStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DataInitializer {

    private final BidStatusRepository bidStatusRepository;

    @Autowired
    public DataInitializer(BidStatusRepository bidStatusRepository) {
        this.bidStatusRepository = bidStatusRepository;
    }

    @PostConstruct
    public void initialize() {
        initializeBidStatus("APPROVED");
        initializeBidStatus("REJECTED");
        initializeBidStatus("PENDING");
        initializeBidStatus("CANCELED");
        // Add more statuses as needed
    }

    private void initializeBidStatus(String statusName) {
        if (!bidStatusRepository.findByName(statusName).isPresent()) {
            BidStatus bidStatus = new BidStatus(statusName);
            bidStatusRepository.save(bidStatus);
        }
    }
}
