package com.khooch.carsalesportal.service;

import com.khooch.carsalesportal.entity.Appointment;

import java.util.List;

public interface AppointmentService {

    Appointment bookAppointment(Appointment appointment, String username);

    void approveAppointment(Long id);

    void denyAppointment(Long id);

    List<Appointment> findAll();

    List<Appointment> findByUser(String username);

    List<Appointment> getAllAppointments();
}
