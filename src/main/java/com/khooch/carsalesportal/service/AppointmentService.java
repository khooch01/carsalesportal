package com.khooch.carsalesportal.service;

import com.khooch.carsalesportal.entity.Appointment;

public interface AppointmentService {
    void bookAppointment(Appointment appointment, String username);
    void approveAppointment(Long id);
    void denyAppointment(Long id);
    Object findAll();
    Object findByUser(String name);
}
