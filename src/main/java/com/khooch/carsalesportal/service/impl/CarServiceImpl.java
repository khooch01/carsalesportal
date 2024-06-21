package com.khooch.carsalesportal.service.impl;

import com.khooch.carsalesportal.entity.Car;
import com.khooch.carsalesportal.entity.User;
import com.khooch.carsalesportal.repository.CarRepository;
import com.khooch.carsalesportal.repository.UserRepository;
import com.khooch.carsalesportal.service.CarService;
import com.khooch.carsalesportal.service.StorageService;
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
    private UserRepository userRepository;

    @Autowired
    private StorageService storageService;

    @Override
    public Car saveCar(Car car, MultipartFile imageFile, String username) {
        try {
            String imagePath = storageService.store(imageFile);
            car.setImagePath(imagePath);

            User user = userRepository.findByUsername(username);
            car.setUser(user);

            return carRepository.save(car);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + e.getMessage());
        }
    }

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    public Car getCarById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }
}
