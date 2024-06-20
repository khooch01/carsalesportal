package com.khooch.carsalesportal.controller;

import com.khooch.carsalesportal.entity.Car;
import com.khooch.carsalesportal.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public String listCars(Model model) {
        List<Car> cars = carService.getAllCars();
        model.addAttribute("cars", cars);
        return "car_list";
    }

    @GetMapping("/add")
    public String showAddCarForm(Model model) {
        model.addAttribute("car", new Car());
        return "car_form";
    }

    @PostMapping("/add")
    public String addCar(@Valid @ModelAttribute("car") Car car, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "car_form";
        }
        carService.saveCar(car);
        return "redirect:/cars";
    }

    @GetMapping("/edit/{id}")
    public String showEditCarForm(@PathVariable Long id, Model model) {
        Car car = carService.getCarById(id);
        model.addAttribute("car", car);
        return "car_form";
    }

    @PostMapping("/edit/{id}")
    public String editCar(@PathVariable Long id, @Valid @ModelAttribute("car") Car car, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "car_form";
        }
        car.setId(id);
        carService.saveCar(car);
        return "redirect:/cars";
    }

    @GetMapping("/delete/{id}")
    public String deleteCar(@PathVariable Long id) {
        carService.deleteCarById(id);
        return "redirect:/cars";
    }

    @GetMapping("/{id}")
    public String viewCarDetails(@PathVariable Long id, Model model) {
        Car car = carService.getCarById(id);
        model.addAttribute("car", car);
        return "car_details";
    }
}
