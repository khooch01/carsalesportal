package com.khooch.carsalesportal.service.impl;

import com.khooch.carsalesportal.entity.Car;
import com.khooch.carsalesportal.repository.CarRepository;
import com.khooch.carsalesportal.service.CarService;
import com.khooch.carsalesportal.service.StorageService;
import com.khooch.carsalesportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private StorageService storageService;

    @Autowired
    private UserService userService;

    @Override
    public void saveCar(Car car, MultipartFile imageFile, String username) throws IOException {
        String imagePath = storageService.store(imageFile);
        car.setImagePath(imagePath);
        car.setUser(userService.findByUsername(username));
        carRepository.save(car);
    }

    @Override
    public void deactivateCar(Long id, String username) {
        Car car = carRepository.findByIdAndUserUsername(id, username);
        if (car != null) {
            car.setActive(false);
            carRepository.save(car);
        }
    }

    @Override
    public void activateCar(Long id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new RuntimeException("Car not found"));
        car.setActive(true);
        carRepository.save(car);
    }

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    public List<Car> getCarsByUser(String username) {
        return carRepository.findByUserUsername(username);
    }
}
