package com.khooch.carsalesportal.controller;

import com.khooch.carsalesportal.entity.Car;
import com.khooch.carsalesportal.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/user/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public String viewCars(Model model) {
        List<Car> cars = carService.getAllCars();
        model.addAttribute("cars", cars);
        return "user/view_cars";
    }

    @GetMapping("/add")
    public String showAddCarForm(Model model) {
        model.addAttribute("car", new Car());
        return "user/add_car";
    }

    @PostMapping("/add")
    public String addCar(@ModelAttribute Car car,
                         @RequestParam("imageFile") MultipartFile imageFile,
                         Authentication authentication,
                         Model model) {
        String username = authentication.getName();
        carService.saveCar(car, imageFile, username);
        return "redirect:/user/cars";
    }

    @GetMapping("/edit/{id}")
    public String showEditCarForm(@PathVariable Long id, Model model) {
        Car car = carService.getCarById(id);
        model.addAttribute("car", car);
        return "user/edit_car";
    }

    @PostMapping("/edit/{id}")
    public String editCar(@PathVariable Long id,
                          @ModelAttribute Car car,
                          @RequestParam("imageFile") MultipartFile imageFile,
                          Authentication authentication,
                          Model model) {
        String username = authentication.getName();
        carService.saveCar(car, imageFile, username);
        return "redirect:/user/cars";
    }

    @PostMapping("/delete/{id}")
    public String deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return "redirect:/user/cars";
    }

    @GetMapping("/details/{id}")
    public String viewCarDetails(@PathVariable Long id, Model model) {
        Car car = carService.getCarById(id);
        model.addAttribute("car", car);
        return "user/car_details";
    }
}
