package com.khooch.carsalesportal.service.impl;

import com.khooch.carsalesportal.entity.Appointment;
import com.khooch.carsalesportal.repository.AppointmentRepository;
import com.khooch.carsalesportal.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Appointment bookAppointment(Appointment appointment, String username) {
        appointment.setUsername(username);
        return appointmentRepository.save(appointment);
    }

    @Override
    public void approveAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid appointment Id:" + id));
        appointment.setApproved(true);
        appointmentRepository.save(appointment);
    }

    @Override
    public void denyAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public List<Appointment> findByUser(String username) {
        return appointmentRepository.findByUsername(username);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
}
