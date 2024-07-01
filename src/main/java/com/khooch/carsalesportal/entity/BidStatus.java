package com.khooch.carsalesportal.entity;

import jakarta.persistence.*;

@Entity
public class BidStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;

    @Column(nullable = false, unique = true)
    private String name;

    // Constructors, getters, and setters

    public BidStatus() {
        // Default constructor
    }

    public BidStatus(String name) {
        this.name = name;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}