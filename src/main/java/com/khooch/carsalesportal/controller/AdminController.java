package com.khooch.carsalesportal.controller;

import com.khooch.carsalesportal.entity.Appointment;
import com.khooch.carsalesportal.entity.Car;
import com.khooch.carsalesportal.entity.User;
import com.khooch.carsalesportal.service.AppointmentService;
import com.khooch.carsalesportal.service.CarService;
import com.khooch.carsalesportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CarService carService;

    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/users")
    public String viewUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/view_users";
    }

    @PostMapping("/users/makeAdmin/{id}")
    public String makeAdmin(@PathVariable Long id) {
        userService.makeAdmin(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/cars")
    public String viewCars(Model model) {
        model.addAttribute("cars", carService.getAllCars());
        return "admin/view_cars";
    }

    @PostMapping("/cars/activate/{id}")
    public String activateCar(@PathVariable Long id) {
        carService.activateCar(id);
        return "redirect:/admin/cars";
    }

    @PostMapping("/cars/deactivate/{id}")
    public String deactivateCar(@PathVariable Long id) {
        carService.deactivateCar(id, null);
        return "redirect:/admin/cars";
    }

    @GetMapping("/appointments")
    public String viewAppointments(Model model) {
        model.addAttribute("appointments", appointmentService.findAll());
        return "admin/view_appointments";
    }

    @PostMapping("/appointments/approve/{id}")
    public String approveAppointment(@PathVariable Long id) {
        appointmentService.approveAppointment(id);
        return "redirect:/admin/appointments";
    }

    @PostMapping("/appointments/deny/{id}")
    public String denyAppointment(@PathVariable Long id) {
        appointmentService.denyAppointment(id);
        return "redirect:/admin/appointments";
    }
}
