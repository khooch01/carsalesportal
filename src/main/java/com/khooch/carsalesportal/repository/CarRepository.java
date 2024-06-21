package com.khooch.carsalesportal.repository;

import com.khooch.carsalesportal.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
