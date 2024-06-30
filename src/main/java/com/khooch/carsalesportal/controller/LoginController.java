package com.khooch.carsalesportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.khooch.carsalesportal.dto.UserRegistrationDto;
import com.khooch.carsalesportal.entity.Appointment;
import com.khooch.carsalesportal.entity.Car;
import com.khooch.carsalesportal.entity.User;
import com.khooch.carsalesportal.repository.CarRepository;
import com.khooch.carsalesportal.service.AppointmentService;
import com.khooch.carsalesportal.service.CarService;
import com.khooch.carsalesportal.service.UserService;

import jakarta.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private CarService carService;
    
    @Autowired
    private AppointmentService appointmentService;
    private CarRepository carRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user/home")
    public String userHome(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        List<Car> userCars = carService.findByUser(user);
        List<Appointment> approvedAppointments = appointmentService.findApprovedByUser(user);
        model.addAttribute("userCars", userCars);
        model.addAttribute("approvedAppointments", approvedAppointments);
        return "user/home";
    }

    @GetMapping("/admin/home")
    public String adminHome() {
        return "admin/home";
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "registration";
    }

    @PostMapping("/register")
    public String registerUserAccount(@Valid @ModelAttribute("user") UserRegistrationDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registration";
        }
        userService.save(userDto);
        return "redirect:/login";
    }

    
    @GetMapping("/search")
    public String searchCars(@RequestParam(required = false) String make,
                             @RequestParam(required = false) String modelParam,
                             @RequestParam(required = false) Integer year,
                             @RequestParam(required = false) Integer priceMin,
                             @RequestParam(required = false) Integer priceMax,
                             Model model) {
        List<Car> searchResults = carRepository.searchCars(make, modelParam, year, priceMin, priceMax);
        model.addAttribute("cars", searchResults);
        return "searchResults"; // Name of the view to display search results
    }
    
}
