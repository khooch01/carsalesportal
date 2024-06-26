package com.khooch.carsalesportal.repository;

import com.khooch.carsalesportal.entity.Car;
import com.khooch.carsalesportal.entity.User;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByMakeAndModelAndYearAndPriceBetween(String make, String model, Integer year, Integer minPrice, Integer maxPrice);
    List<Car> findByUserUsername(String username);
    List<Car> findByUser(User user);
    List<Car> findAllByActiveTrue();
    
    @EntityGraph(attributePaths = "bids")
    Optional<Car> findById(Long id);
}
