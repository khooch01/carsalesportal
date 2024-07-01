package com.khooch.carsalesportal.service;

import com.khooch.carsalesportal.dto.CarDto;
import com.khooch.carsalesportal.entity.Appointment;
import com.khooch.carsalesportal.entity.Bid;
import com.khooch.carsalesportal.entity.Car;
import com.khooch.carsalesportal.entity.User;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public interface CarService {
    Car save(Car car);
    Car update(Car car);
    Car save(CarDto carDto, MultipartFile imageFile);
    List<Car> findByUserName(String email);
    List<Car> findAll();
    Optional<Car> findById(Long id);
    void delete(Long id);
    List<Car> search(String make, String model, Integer year, Integer price, Integer mileage);
    void deactivate(Long id);
    List<Car> getAllCars();
    void addCar(Car car);
    Car getCarById(Long carId);
    void updateCar(Car car);
    void deleteCar(Long carId);
    List<Car> findByUser(User user);
    void saveOrUpdateCar(Car car);
    void deactivateCar(Long carId);
    void bookAppointment(Appointment appointment);
    void postBid(Bid bid);
    void saveCar(CarDto carDto, byte[] imageData, User user);
    List<Car> findAllAvailableCars();
    double calculateHighestPrice(List<Car> cars);
    List<Car> findCarsWithApprovedBidsByUser(User user);
    List<Car> getAvailableCars();
}
