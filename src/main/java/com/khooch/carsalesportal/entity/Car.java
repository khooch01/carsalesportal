package com.khooch.carsalesportal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Make is mandatory")
    @Column(nullable = false)
    private String make;

    @NotBlank(message = "Model is mandatory")
    @Column(nullable = false)
    private String model;

    @Min(value = 2000, message = "Year must be between 2000 and 2024")
    @Max(value = 2024, message = "Year must be between 2000 and 2024")
    @Column(nullable = false)
    private Integer year;

    @Min(value = 0, message = "Price must be a positive value")
    @Column(nullable = false)
    private Integer price;

    @Min(value = 0, message = "Mileage must be a positive value")
    @Column(nullable = false)
    private Integer mileage;

    @NotBlank(message = "Color is mandatory")
    @Column(nullable = false)
    private String color;

    @Size(max = 1000, message = "Description must be less than 1000 characters")
    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Lob
    @Column(name = "image_data", nullable = true)
    private byte[] imageData;

    @Column(name = "image_url", nullable = true)
    private String imageUrl;

    @OneToMany(mappedBy = "car")
    private List<Bid> bids;

    @Transient
    private BigDecimal highestBidAmount;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public BigDecimal getHighestBidAmount() {
        if (bids != null && !bids.isEmpty()) {
            Optional<Bid> highestBid = bids.stream()
                    .filter(bid -> bid.getStatus() != null && bid.getStatus().equals("APPROVED"))
                    .max(Comparator.comparing(Bid::getBidAmount));
            return highestBid.map(Bid::getBidAmount).orElse(BigDecimal.ZERO);
        }
        return BigDecimal.ZERO;
    }
}
