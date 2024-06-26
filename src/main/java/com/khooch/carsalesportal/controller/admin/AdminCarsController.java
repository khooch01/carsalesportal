package com.khooch.carsalesportal.controller.admin;

import com.khooch.carsalesportal.entity.Car;
import com.khooch.carsalesportal.entity.User;
import com.khooch.carsalesportal.service.UserService;


import org.springframework.security.core.context.SecurityContextHolder;
import com.khooch.carsalesportal.service.CarService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminCarsController {

    private final CarService carService;
    private final UserService userService;

    public AdminCarsController(CarService carService, UserService userService) {
        this.carService = carService;
        this.userService = userService;
    }

    @GetMapping("/admin/cars")
    public String listCars(Model model) {
        List<Car> cars = carService.getAllCars();
        model.addAttribute("cars", cars);
        return "admin/viewCars";
    }

    @GetMapping("/admin/cars/add")
    public String showAddCarForm(Model model) {
        model.addAttribute("car", new Car());
        return "admin/addCar";
    }

    @PostMapping("/admin/cars/add")
    public String addCar(@ModelAttribute("car") Car car) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Assuming username is used for retrieval

        // Find user by username
        User user = userService.findUserByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }

        // Set user for the car
        car.setUser(user);

        carService.addCar(car);
        return "redirect:/admin/cars";
    }

    @GetMapping("/admin/cars/edit/{id}")
    public String showEditCarForm(@PathVariable Long id, Model model) {
        Car car = carService.getCarById(id);
        model.addAttribute("car", car);
        return "admin/editCar";
    }

    @PostMapping("/admin/cars/edit/{id}")
    public String editCar(@PathVariable Long id, @ModelAttribute("car") Car updatedCar) {
        // Retrieve the existing car from the database
        Car existingCar = carService.getCarById(id);
    
        // Ensure existingCar is not null (you may want to add error handling if null)
    
        // Update the existing car's fields with the new values
        existingCar.setActive(updatedCar.isActive());
        existingCar.setColor(updatedCar.getColor());
        existingCar.setDescription(updatedCar.getDescription());
        existingCar.setMake(updatedCar.getMake());
        existingCar.setMileage(updatedCar.getMileage());
        existingCar.setModel(updatedCar.getModel());
        existingCar.setPrice(updatedCar.getPrice());
        existingCar.setYear(updatedCar.getYear());
    
        // You may need to handle image_data separately if it's uploaded via a file input
    
        // Save the updated car back to the database
        carService.updateCar(existingCar);
    
        return "redirect:/admin/cars";
    }

    @PostMapping("/admin/cars/delete/{id}")
    public String deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return "redirect:/admin/cars";
    }
}
