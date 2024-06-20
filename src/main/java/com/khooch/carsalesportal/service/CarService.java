package com.khooch.carsalesportal.service;

import com.khooch.carsalesportal.entity.Car;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CarService {
    void saveCar(Car car, MultipartFile imageFile, String username) throws IOException;
    void deactivateCar(Long id, String username);
    void activateCar(Long id);
    List<Car> getAllCars();
    List<Car> getCarsByUser(String username);
}
