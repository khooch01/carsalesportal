package com.khooch.carsalesportal.controller;

import com.khooch.carsalesportal.entity.Car;
import com.khooch.carsalesportal.entity.User;
import com.khooch.carsalesportal.service.CarService;
import com.khooch.carsalesportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private CarService carService;

    @GetMapping("/home")
    public String adminHome() {
        return "admin/home";
    }

    @GetMapping("/users")
    public String viewUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin/view_users";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/cars")
    public String viewCars(Model model) {
        List<Car> cars = carService.getAllCars();
        model.addAttribute("cars", cars);
        return "admin/view_cars";
    }

    @PostMapping("/cars/delete/{id}")
    public String deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return "redirect:/admin/cars";
    }
}
