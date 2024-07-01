package com.khooch.carsalesportal.service.impl;

import com.khooch.carsalesportal.dto.BidDto;
import com.khooch.carsalesportal.entity.Bid;
import com.khooch.carsalesportal.entity.BidStatus;
import com.khooch.carsalesportal.entity.Car;
import com.khooch.carsalesportal.entity.User;
import com.khooch.carsalesportal.repository.BidRepository;
import com.khooch.carsalesportal.repository.CarRepository;
import com.khooch.carsalesportal.repository.UserRepository;
import com.khooch.carsalesportal.service.BidService;
import com.khooch.carsalesportal.service.BidStatusService;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BidServiceImpl implements BidService {

    private final BidRepository bidRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final BidStatusService bidStatusService;

    public BidServiceImpl(BidRepository bidRepository, UserRepository userRepository, CarRepository carRepository, BidStatusService bidStatusService) {
        this.bidRepository = bidRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.bidStatusService = bidStatusService;
    }

    @Override
    @Transactional
    public Bid save(BidDto bidDto) {
        Bid bid = new Bid();

        // Convert double to BigDecimal
        BigDecimal bidAmount = bidDto.getAmount();

        bid.setBidAmount(bidAmount); // Set the bid amount

        Car car = carRepository.findById(bidDto.getCarId())
                                .orElseThrow(() -> new RuntimeException("Car not found"));
        bid.setCar(car);

        User user = userRepository.findById(bidDto.getUserId())
                                  .orElseThrow(() -> new RuntimeException("User not found"));
        bid.setUser(user);

        // Set the bid status based on bidDto status name
        BidStatus status = bidStatusService.findOrCreateByName(bidDto.getStatus());
        bid.setStatus(status); // Set BidStatus object directly

        return bidRepository.save(bid);
    }

    @Override
    public Bid findById(Long id) {
        return bidRepository.findById(id).orElse(null);
    }

    @Override
    public List<Bid> findAll() {
        return bidRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        bidRepository.deleteById(id);
    }

    @Override
    public List<Bid> getAllBids() {
        return bidRepository.findAll();
    }

    @Override
    @Transactional
    public void approveBid(Long bidId) {
        Optional<Bid> optionalBid = bidRepository.findById(bidId);
        if (optionalBid.isPresent()) {
            Bid bid = optionalBid.get();
            BidStatus approvedStatus = bidStatusService.findOrCreateByName("APPROVED");
            bid.setStatus(approvedStatus); // Set BidStatus object directly
            bidRepository.save(bid);
        } else {
            throw new IllegalArgumentException("Bid with id " + bidId + " not found");
        }
    }

    @Override
    @Transactional
    public void denyBid(Long bidId) {
        Optional<Bid> optionalBid = bidRepository.findById(bidId);
        if (optionalBid.isPresent()) {
            Bid bid = optionalBid.get();
            BidStatus deniedStatus = bidStatusService.findOrCreateByName("DENIED");
            bid.setStatus(deniedStatus); // Set BidStatus object directly
            bidRepository.save(bid);
        } else {
            throw new IllegalArgumentException("Bid with id " + bidId + " not found");
        }
    }

    @Override
    public Bid getBidById(Long id) {
        return bidRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void updateBidStatus(Long bidId, BidStatus newStatus) {
        Optional<Bid> optionalBid = bidRepository.findById(bidId);
        if (optionalBid.isPresent()) {
            Bid bid = optionalBid.get();
            bid.setStatus(newStatus);
            bidRepository.save(bid);
        } else {
            throw new EntityNotFoundException("Bid with id " + bidId + " not found.");
        }
    }

    @Override
    @Transactional
    public BigDecimal getHighestBidPriceForCar(Long carId) {
        Optional<Bid> highestBid = bidRepository.findHighestBidForCar(carId);
        return highestBid.map(Bid::getBidAmount).orElse(BigDecimal.ZERO);
    }

    @Override
    public Optional<Bid> findHighestBidForCar(Long carId) {
        return bidRepository.findTopByCarIdOrderByBidAmountDesc(carId);
    }

    @Override
    @Transactional
    public void save(Bid bid) {
        bidRepository.save(bid);
    }

    public List<Bid> findBidsByCarIdAndUserId(Long carId, Long userId) {
        return bidRepository.findByCarIdAndUserId(carId, userId);
    }

    public void rejectBid(Long id) {
        Optional<Bid> bidOptional = bidRepository.findById(id);
        if (!bidOptional.isPresent()) {
            throw new IllegalArgumentException("Bid not found");
        }

        Bid bid = bidOptional.get();
        BidStatus rejectedStatus = bidStatusService.findOrCreateByName("Rejected");
        bid.setStatus(rejectedStatus);

        bidRepository.save(bid);
    }

    @Override
    public List<Bid> findApprovedBidsByUser(User user) {
        return bidRepository.findByUserAndStatusName(user, "Approved");
    }
}