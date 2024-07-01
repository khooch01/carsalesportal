package com.khooch.carsalesportal;

import com.khooch.carsalesportal.entity.Car;
import com.khooch.carsalesportal.entity.User;
import com.khooch.carsalesportal.repository.CarRepository;
import com.khooch.carsalesportal.service.impl.CarServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    private Car validCar;
    private Car invalidCar;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setUsername("user1");

        validCar = new Car();
        validCar.setMake("Toyota");
        validCar.setModel("Corolla");
        validCar.setYear(2020);
        validCar.setPrice(25000);
        validCar.setMileage(5000);
        validCar.setColor("Red");
        validCar.setUser(user);

        invalidCar = new Car();
        invalidCar.setMake("");
        invalidCar.setModel("Corolla");
        invalidCar.setYear(3000);
        invalidCar.setPrice(-25000);
        invalidCar.setMileage(5000);
        invalidCar.setColor("Red");
        invalidCar.setUser(user);
    }

    @Test
    public void testPostCarWithoutLogin() {
        // Arrange
        Car car = new Car();
        car.setMake("Toyota");
        car.setModel("Corolla");
        car.setYear(2020);
        car.setPrice(25000);
        car.setMileage(5000);
        car.setColor("Red");
        car.setUser(null);  // User not set, indicating not logged in

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            carService.save(car);
        });

        assertEquals("User must be logged in to post a car", exception.getMessage());
    }

    @Test
    public void testPostCarWithInvalidData() {
        // Arrange
        // Using the invalidCar initialized in the setup

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            carService.save(invalidCar);
        });

        // Verify that the carRepository's save method was never called
        verify(carRepository, never()).save(invalidCar);
    }
}
