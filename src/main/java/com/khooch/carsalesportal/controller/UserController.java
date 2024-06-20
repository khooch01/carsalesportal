package com.khooch.carsalesportal.controller;

import com.khooch.carsalesportal.entity.Appointment;
import com.khooch.carsalesportal.entity.Bid;
import com.khooch.carsalesportal.entity.Car;
import com.khooch.carsalesportal.entity.User;
import com.khooch.carsalesportal.service.AppointmentService;
import com.khooch.carsalesportal.service.BidService;
import com.khooch.carsalesportal.service.CarService;
import com.khooch.carsalesportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CarService carService;

    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private BidService bidService;

    @GetMapping("/cars")
    public String viewCars(Model model, Principal principal) {
        model.addAttribute("cars", carService.getCarsByUser(principal.getName()));
        return "user/view_cars";
    }

    @GetMapping("/cars/add")
    public String showAddCarForm(Model model) {
        model.addAttribute("car", new Car());
        return "user/add_car";
    }

    @PostMapping("/cars/add")
    public String addCar(@ModelAttribute("car") Car car, @RequestParam("imageFile") MultipartFile imageFile, Principal principal, Model model) {
        try {
            carService.saveCar(car, imageFile, principal.getName());
        } catch (IOException e) {
            model.addAttribute("error", "Error uploading image");
            return "user/add_car";
        }
        return "redirect:/user/cars";
    }

    @PostMapping("/cars/deactivate/{id}")
    public String deactivateCar(@PathVariable Long id, Principal principal) {
        carService.deactivateCar(id, principal.getName());
        return "redirect:/user/cars";
    }

    @GetMapping("/profile")
    public String viewProfile(Model model, Principal principal) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        return "user/profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute("user") User user, Principal principal) {
        userService.updateUser(user, principal.getName());
        return "redirect:/user/profile";
    }

    @GetMapping("/appointments")
    public String viewAppointments(Model model, Principal principal) {
        model.addAttribute("appointments", appointmentService.findByUser(principal.getName()));
        return "user/appointments";
    }

    @GetMapping("/appointments/book")
    public String showBookAppointmentForm(Model model) {
        model.addAttribute("appointment", new Appointment());
        return "user/book_appointment";
    }

    @PostMapping("/appointments/book")
    public String bookAppointment(@ModelAttribute("appointment") Appointment appointment, Principal principal) {
        appointmentService.bookAppointment(appointment, principal.getName());
        return "redirect:/user/appointments";
    }

    @GetMapping("/bids")
    public String viewBids(Model model, Principal principal) {
        model.addAttribute("bids", bidService.findByUser(principal.getName()));
        return "user/bids";
    }

    @GetMapping("/bids/post")
    public String showPostBidForm(Model model) {
        model.addAttribute("bid", new Bid());
        return "user/post_bid";
    }

    @PostMapping("/bids/post")
    public String postBid(@ModelAttribute("bid") Bid bid, Principal principal) {
        bidService.postBid(bid, principal.getName());
        return "redirect:/user/bids";
    }
}
