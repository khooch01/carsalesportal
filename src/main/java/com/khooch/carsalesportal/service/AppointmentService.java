package com.khooch.carsalesportal.service;

import com.khooch.carsalesportal.dto.AppointmentDto;
import com.khooch.carsalesportal.entity.Appointment;
import com.khooch.carsalesportal.entity.User;

import java.util.List;

public interface AppointmentService {
    Appointment save(AppointmentDto appointmentDto);
    Appointment findById(Long id);
    List<Appointment> findAll();
    void deleteById(Long id);
    void approveAppointment(Long appointmentId);
    void denyAppointment(Long appointmentId);
    List<Appointment> getAllAppointments();
    void bookAppointment(User user, Long carId, String date, String time);
    List<String> getAvailableDates();
    void saveAppointment(Long bidId, String appointmentDate);
    List<Appointment> findApprovedByUser(User user);
    boolean checkIfCarHasApprovedBid(User user, Long carId);
}
