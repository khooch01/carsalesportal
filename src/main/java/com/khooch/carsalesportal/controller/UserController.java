package com.khooch.carsalesportal.controller;

import com.khooch.carsalesportal.dto.BidDto;
import com.khooch.carsalesportal.dto.CarDto;
import com.khooch.carsalesportal.dto.UserDto;
import com.khooch.carsalesportal.entity.Appointment;
import com.khooch.carsalesportal.entity.Bid;
import com.khooch.carsalesportal.entity.BidStatus;
import com.khooch.carsalesportal.entity.Car;
import com.khooch.carsalesportal.entity.User;
import com.khooch.carsalesportal.repository.CarRepository;
import com.khooch.carsalesportal.service.AppointmentService;
import com.khooch.carsalesportal.service.BidService;
import com.khooch.carsalesportal.service.BidStatusService;
import com.khooch.carsalesportal.service.CarService;
import com.khooch.carsalesportal.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final CarService carService;
    private final AppointmentService appointmentService;
    private final BidService bidService;
    private final BidStatusService bidStatusService;
    private final CarRepository carRepository;



    @Autowired
    public UserController(UserService userService, CarService carService, AppointmentService appointmentService, BidService bidService, BidStatusService bidStatusService, CarRepository carRepository) {
        this.userService = userService;
        this.carService = carService;
        this.appointmentService = appointmentService;
        this.bidService = bidService;
        this.bidStatusService = bidStatusService;
        this.carRepository = carRepository;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/cars/post")
    public String postCarForm(Model model) {
        model.addAttribute("carDto", new CarDto());
        return "user/postCar";
    }

    @PostMapping("/cars/post")
    public String postCar(@Valid @ModelAttribute("carDto") CarDto carDto,
                          BindingResult bindingResult,
                          @RequestParam("imageFile") MultipartFile imageFile,
                          @AuthenticationPrincipal UserDetails userDetails,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "user/postCar";
        }

        try {
            // Validate image file if required
            if (imageFile.isEmpty()) {
                bindingResult.rejectValue("imageData", "error.imageFile", "Image file is required");
                return "user/postCar";
            }

            // Save the image to a static location
            String imageUrl = saveImageToFileSystem(imageFile);

            // Retrieve the user details (assuming you have a userService to find user by username)
            User user = userService.findByUsername(userDetails.getUsername());

            // Create a new Car entity from carDto
            Car car = new Car();
            car.setMake(carDto.getMake());
            car.setModel(carDto.getModel());
            car.setYear(carDto.getYear());
            car.setPrice(carDto.getPrice());
            car.setMileage(carDto.getMileage());
            car.setColor(carDto.getColor());
            car.setDescription(carDto.getDescription());
            car.setUser(user); // Set the user who posted the car
            car.setImageUrl(imageUrl); // Set image URL
            car.setActive(true);

            // Save the Car entity to the database
            carService.save(car);

            // Redirect to home page or success page
            return "redirect:/user/home";
        } catch (Exception e) {
            // Handle exceptions, log errors, etc.
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to post car: " + e.getMessage());
            return "redirect:/user/cars/post";
        }
    }
    
    private String saveImageToFileSystem(MultipartFile imageFile) throws IOException {
        return "static/image";
    }
    

    @GetMapping("/profile/update")
    public String updateUserProfileForm(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        // Do not set password here as we don't want to display it in the form
        model.addAttribute("userDto", userDto);
        return "user/updateProfile";
    }

    @PostMapping("/profile/update")
    public String updateUserProfile(@ModelAttribute("userDto") UserDto userDto) {
        User user = userService.findById(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            // Update password only if a new password is provided
            user.setPassword(userDto.getPassword());
        }
        userService.save(user);
        return "redirect:/user/home";
    }

    @PostMapping("/cars/deactivate/{id}")
    public String deactivateCar(@PathVariable Long id) {
        carService.deactivateCar(id);
        return "redirect:/user/home";
    }
    
    @GetMapping("/appointments/book")
    public String bookAppointmentForm(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        
        // Find cars with approved bids
        List<Car> userCarsWithApprovedBids = carService.findCarsWithApprovedBidsByUser(user);
        
        model.addAttribute("userCars", userCarsWithApprovedBids);
        return "user/selectAppointment";
    }

    @PostMapping("/appointments/book")
    public String bookAppointment(@RequestParam Long carId, @RequestParam String date, @RequestParam String time, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        
        // Check if the selected car has an approved bid
        boolean hasApprovedBid = appointmentService.checkIfCarHasApprovedBid(user, carId);
        
        if (!hasApprovedBid) {
            // Handle case where car does not have an approved bid
            // Redirect or show an error message
            return "redirect:/user/home"; // Redirect to user home or appropriate error page
        }
        
        // Book the appointment
        appointmentService.bookAppointment(user, carId, date, time);
        return "redirect:/appointments/book";
    }
    
    @GetMapping("/bidding/post")
    public String showBiddingForm(Model model) {
        List<Car> availableCars = carService.findAllAvailableCars();
        Map<Long, BigDecimal> highestBidPrices = new HashMap<>();
    
        for (Car car : availableCars) {
            Optional<Bid> highestBid = bidService.findHighestBidForCar(car.getId());
            highestBid.ifPresent(bid -> highestBidPrices.put(car.getId(), bid.getBidAmount()));
        }
    
        BidDto bidDto = new BidDto();
        model.addAttribute("availableCars", availableCars);
        model.addAttribute("highestBidPrices", highestBidPrices);
        model.addAttribute("bidDto", bidDto);
        model.addAttribute("successMessage", ""); // Add an empty success message
        model.addAttribute("errorMessage", ""); // Add an empty error message
    
        return "user/postBidding";
    }

    @PostMapping("/bidding/post")
    public String postBid(@ModelAttribute("bidDto") @Validated BidDto bidDto,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes,
                          @AuthenticationPrincipal UserDetails userDetails) {
        if (bindingResult.hasErrors()) {
            return "user/postBidding";
        }

        try {
            User user = userService.findByUsername(userDetails.getUsername());
            Optional<Car> carOptional = carService.findById(bidDto.getCarId());
            if (!carOptional.isPresent()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Car not found");
                return "redirect:/user/bidding/post";
            }

            Car car = carOptional.get();

            Bid bid = new Bid();
            bid.setBidAmount(bidDto.getAmount());
            bid.setCar(car);
            bid.setUser(user);

            // Find or create BidStatus "Pending"
            BidStatus pendingStatus = bidStatusService.findOrCreateByName("Pending");
            bid.setStatus(pendingStatus); // Set BidStatus object

            bidService.save(bid);
            redirectAttributes.addFlashAttribute("successMessage", "Bid posted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to post bid: " + e.getMessage());
        }
        return "redirect:/user/bidding/post";
    }

    @GetMapping("/cars/edit/{id}")
    public String editCarForm(@PathVariable Long id, Model model) {
        Optional<Car> carOptional = carService.findById(id);
        if (carOptional.isPresent()) {
            Car car = carOptional.get();
            CarDto carDto = new CarDto();
            carDto.setId(car.getId());
            carDto.setMake(car.getMake());
            carDto.setModel(car.getModel());
            carDto.setYear(car.getYear());
            carDto.setPrice(car.getPrice());
            carDto.setMileage(car.getMileage());
            carDto.setColor(car.getColor());
            carDto.setDescription(car.getDescription());
            model.addAttribute("carDto", carDto);
            return "user/editCar";
        } else {
            return "redirect:/user/home";
        }
    }

    @PostMapping("/cars/update")
    public String updateCar(@ModelAttribute("carDto") CarDto carDto,
                            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                            @AuthenticationPrincipal UserDetails userDetails,
                            RedirectAttributes redirectAttributes) {
        try {
            Optional<Car> carOptional = carService.findById(carDto.getId());
            if (!carOptional.isPresent()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Car not found");
                return "redirect:/user/home";
            }
    
            User user = userService.findByUsername(userDetails.getUsername());
            Car car = carOptional.get();
    
            // Update car details
            car.setMake(carDto.getMake());
            car.setModel(carDto.getModel());
            car.setYear(carDto.getYear());
            car.setPrice(carDto.getPrice());
            car.setMileage(carDto.getMileage());
            car.setColor(carDto.getColor());
            car.setDescription(carDto.getDescription());
            car.setUser(user);
            car.setActive(true);
    
            // Update image if provided
            if (imageFile != null && !imageFile.isEmpty()) {
                byte[] imageData = imageFile.getBytes();
                String imageUrl = saveImageToFileSystem(imageFile);
                car.setImageData(imageData);
                car.setImageUrl(imageUrl);
            }
    
            carService.save(car);
            return "redirect:/user/home";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update car: " + e.getMessage());
            return "redirect:/error"; // Redirect to an error page
        }
    }
    

    @Value("${upload.path:static/image}")
    private String uploadPath;


    @GetMapping("/bids/appointment/{bidId}")
    public String selectAppointmentForm(@PathVariable Long bidId, Model model) {
        Bid bid = bidService.findById(bidId);
        model.addAttribute("bid", bid);
        model.addAttribute("availableDates", appointmentService.getAvailableDates());
        return "user/selectAppointment";
    }

    @PostMapping("/bids/appointment/{bidId}")
    public String saveAppointment(@PathVariable Long bidId, @RequestParam String appointmentDate, RedirectAttributes redirectAttributes) {
        try {
            appointmentService.saveAppointment(bidId, appointmentDate);
            redirectAttributes.addFlashAttribute("successMessage", "Appointment date selected successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to select appointment date: " + e.getMessage());
        }
        return "redirect:/user/home";
    }

}
