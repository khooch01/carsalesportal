package com.khooch.carsalesportal.service.impl;

import com.khooch.carsalesportal.dto.AppointmentDto;
import com.khooch.carsalesportal.entity.Appointment;
import com.khooch.carsalesportal.entity.Bid;
import com.khooch.carsalesportal.entity.Car;
import com.khooch.carsalesportal.entity.User;
import com.khooch.carsalesportal.repository.AppointmentRepository;
import com.khooch.carsalesportal.repository.BidRepository;
import com.khooch.carsalesportal.repository.CarRepository;
import com.khooch.carsalesportal.repository.UserRepository;
import com.khooch.carsalesportal.service.AppointmentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final BidRepository bidRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, UserRepository userRepository, CarRepository carRepository, BidRepository bidRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.bidRepository = bidRepository;
    }

    @Override
    public Appointment save(AppointmentDto appointmentDto) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(appointmentDto.getAppointmentDate());
        Car car = carRepository.findById(appointmentDto.getCarId()).orElseThrow(() -> new RuntimeException("Car not found"));
        appointment.setCar(car);
        User user = userRepository.findById(1L).orElseThrow(() -> new RuntimeException("User not found")); // Placeholder user ID, replace with actual logic
        appointment.setUser(user);
        appointment.setApproved(false); // Default to not approved
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment findById(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public void approveAppointment(Long appointmentId) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);
        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            // Implement approval logic (for example, update appointment status)
            appointment.setStatus("Approved");
            appointmentRepository.save(appointment);
        } else {
            throw new IllegalArgumentException("Appointment with id " + appointmentId + " not found");
        }
    }

    @Override
    public void denyAppointment(Long appointmentId) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);
        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            // Implement denial logic (for example, update appointment status)
            appointment.setStatus("Denied");
            appointmentRepository.save(appointment);
        } else {
            throw new IllegalArgumentException("Appointment with id " + appointmentId + " not found");
        }
    }
    @Override
    public void bookAppointment(User user, Long carId, String date, String time) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + carId));

        // Parse date and time strings into LocalDateTime
        LocalDateTime appointmentDateTime = parseDateTime(date, time);

        Appointment appointment = new Appointment();
        appointment.setUser(user);
        appointment.setCar(car);

        // Convert LocalDateTime to Date
        Date appointmentDate = Date.from(appointmentDateTime.atZone(ZoneId.systemDefault()).toInstant());
        appointment.setAppointmentDate(appointmentDate);

        appointmentRepository.save(appointment);
    }

    // Method to parse date and time strings into LocalDateTime
    private LocalDateTime parseDateTime(String date, String time) {
        // Format date and time strings
        String dateTimeString = date + " " + time;

        // Define the date-time format (adjust according to your input format)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Parse string to LocalDateTime
        return LocalDateTime.parse(dateTimeString, formatter);
    }

    @Override
    public List<String> getAvailableDates() {
        // Example implementation
        return Arrays.asList("2024-07-01", "2024-07-02", "2024-07-03");
    }

    @Override
    public void saveAppointment(Long bidId, String appointmentDate) {
        Bid bid = bidRepository.findById(bidId).orElseThrow(() -> new IllegalArgumentException("Bid not found"));
        bid.setAppointmentDate(appointmentDate);
        bidRepository.save(bid);
    }

    @Override
    public List<Appointment> findApprovedByUser(User user) {
        return appointmentRepository.findByUserAndStatus(user, "Approved");
    }
    
    @Override
    public boolean checkIfCarHasApprovedBid(User user, Long carId) {
        // Retrieve bids for the specified car and user
        Bid bid = bidRepository.findByUserAndCarId(user, carId);

        // Check if there is an approved bid for the specified car
        return bid != null && "Approved".equals(bid.getStatus().getName()); // Assuming BidStatus is accessible via bid.getStatus()
    }
}