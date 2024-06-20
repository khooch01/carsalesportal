package com.khooch.carsalesportal.repository;

import com.khooch.carsalesportal.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByUserUsername(String username);
    Car findByIdAndUserUsername(Long id, String username);
}
