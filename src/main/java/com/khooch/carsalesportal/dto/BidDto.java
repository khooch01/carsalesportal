package com.khooch.carsalesportal.dto;

import java.math.BigDecimal;

public class BidDto {

    private Long carId;

    private BigDecimal amount;

    private Long userId;
    private String status;

    // Default constructor
    public BidDto() {
        this.status = "PENDING"; // Default status
    }

    // Getters and setters
    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}