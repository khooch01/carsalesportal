package com.khooch.carsalesportal.service.impl;

import com.khooch.carsalesportal.dto.CarDto;
import com.khooch.carsalesportal.entity.Appointment;
import com.khooch.carsalesportal.entity.Bid;
import com.khooch.carsalesportal.entity.BidStatus;
import com.khooch.carsalesportal.entity.Car;
import com.khooch.carsalesportal.entity.User;
import com.khooch.carsalesportal.repository.AppointmentRepository;
import com.khooch.carsalesportal.repository.BidRepository;
import com.khooch.carsalesportal.repository.BidStatusRepository;
import com.khooch.carsalesportal.repository.CarRepository;
import com.khooch.carsalesportal.service.CarService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final AppointmentRepository appointmentRepository; // Inject AppointmentRepository
    private final BidRepository bidRepository;
    private final BidStatusRepository bidStatusRepository;

    // Constructor injection of repositories
    public CarServiceImpl(CarRepository carRepository, AppointmentRepository appointmentRepository, BidRepository bidRepository, BidStatusRepository bidStatusRepository) {
        this.carRepository = carRepository;
        this.appointmentRepository = appointmentRepository;
        this.bidRepository = bidRepository;
        this.bidStatusRepository = bidStatusRepository;
    }

    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Car save(CarDto carDto, MultipartFile imageFile) {
        // Convert MultipartFile to byte array
        byte[] imageData;
        try {
            imageData = imageFile.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read image file", e);
        }

        // Create and save the car entity
        Car car = new Car();
        car.setMake(carDto.getMake());
        car.setModel(carDto.getModel());
        car.setYear(carDto.getYear());
        car.setPrice(carDto.getPrice());
        car.setMileage(carDto.getMileage());
        car.setColor(carDto.getColor());
        car.setDescription(carDto.getDescription());
        car.setImageData(imageData); // Set image data
        car.setUser(carDto.getUser());
        car.setActive(true); // Assuming new cars are active by default

        return carRepository.save(car);
    }

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public Optional<Car> findById(Long id) {
        return carRepository.findById(id);  // Change here
    }

    @Override
    public void delete(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public List<Car> search(String make, String model, Integer year, Integer price, Integer mileage) {
        // Implement your search logic here
        return null;
    }
    @Override
    public void deactivate(Long id) {
        // Implement deactivate logic here, e.g., setting active flag to false
        Optional<Car> optionalCar = carRepository.findById(id);
        optionalCar.ifPresent(car -> {
            car.setActive(false);
            carRepository.save(car);
        });
    }

    @Override
    public Car update(Car car) {
        return carRepository.save(car);
    }

    @Override
    public List<Car> findByUserName(String username) {
        return carRepository.findByUserUsername(username);
    }

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    public void addCar(Car car) {
        carRepository.save(car);
    }

    @Override
    public Car getCarById(Long carId) {
        Optional<Car> optionalCar = carRepository.findById(carId);
        if (optionalCar.isPresent()) {
            return optionalCar.get();
        } else {
            throw new IllegalArgumentException("Car with id " + carId + " not found");
        }
    }

    @Override
    public void updateCar(Car car) {
        carRepository.save(car);
    }

    @Override
    public void deleteCar(Long carId) {
        carRepository.deleteById(carId);
    }
    @Override
    public List<Car> findByUser(User user) {
        return carRepository.findByUser(user);
    }

    @Override
    public void saveOrUpdateCar(Car car) {
        carRepository.save(car);
    }

    @Override
    public void deactivateCar(Long carId) {
        Car car = carRepository.findById(carId).orElse(null);
        if (car != null) {
            car.setActive(false);
            carRepository.save(car);
        }
        // Handle if car with given ID is not found
    }
    @Override
    public void bookAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
        // You can add more logic here as needed
    }

    @Override
    public void postBid(Bid bid) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'postBid'");
    }

    @Override
    public void saveCar(CarDto carDto, byte[] imageData, User user) {
        Car car = new Car();
        car.setMake(carDto.getMake());
        car.setModel(carDto.getModel());
        car.setYear(carDto.getYear());
        car.setPrice(carDto.getPrice());
        car.setMileage(carDto.getMileage());
        car.setColor(carDto.getColor());
        car.setDescription(carDto.getDescription());
        car.setImageData(imageData);
        car.setActive(true);
        car.setUser(user);
        carRepository.save(car);
    }
    @Override
    public List<Car> findAllAvailableCars() {
        return carRepository.findAllByActiveTrue(); // Adjust this based on your repository method
    }

    @Override
    public double calculateHighestPrice(List<Car> cars) {
        double highestPrice = 0.0;
        for (Car car : cars) {
            if (car.getPrice() > highestPrice) {
                highestPrice = car.getPrice();
            }
        }
        return highestPrice;
    }
    
    @Override
    public List<Car> findCarsWithApprovedBidsByUser(User user) {
        // Fetch the "APPROVED" bid status from the repository
        Optional<BidStatus> approvedStatus = bidStatusRepository.findByName("APPROVED");

        if (approvedStatus.isPresent()) {
            // Fetch all bids for the user with the "APPROVED" status
            List<Bid> userApprovedBids = bidRepository.findByUserAndStatus(user, approvedStatus.get());

            // Extract cars from the approved bids
            List<Car> carsWithApprovedBids = userApprovedBids.stream()
                    .map(Bid::getCar)
                    .collect(Collectors.toList());

            return carsWithApprovedBids;
        } else {
            // Handle case where "APPROVED" status is not found
            throw new IllegalStateException("Bid status 'APPROVED' not found");
        }
    }

    @Override
    public List<Car> getAvailableCars() {
        return carRepository.findAll(); // Or any other logic to fetch available cars
    }

    @Override
    public List<CarDto> searchCars(String make, String model, Integer year, Integer priceMin, Integer priceMax) {
        List<Car> cars;

        // Check if all parameters are null
        if (make == null && model == null && year == null && priceMin == null && priceMax == null) {
            cars = carRepository.findAll();
        } else {
            cars = carRepository.searchCars(make, model, year, priceMin, priceMax);
        }

        // Map the Car list to CarDto list
        return cars.stream()
                   .map(CarDto::new) // Using constructor reference
                   .collect(Collectors.toList());
    }
}
