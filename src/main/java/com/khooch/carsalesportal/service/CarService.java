package com.khooch.carsalesportal.service;

import com.khooch.carsalesportal.entity.Car;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CarService {
    Car saveCar(Car car, MultipartFile imageFile, String username);
    List<Car> getAllCars();
    Car getCarById(Long id);
    void deleteCar(Long id);
}
