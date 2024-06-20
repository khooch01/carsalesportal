package com.khooch.carsalesportal.service.impl;

import com.khooch.carsalesportal.entity.Appointment;
import com.khooch.carsalesportal.repository.AppointmentRepository;
import com.khooch.carsalesportal.service.AppointmentService;
import com.khooch.carsalesportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserService userService;

    @Override
    public void bookAppointment(Appointment appointment, String username) {
        appointment.setUser(userService.findByUsername(username));
        appointmentRepository.save(appointment);
    }

    @Override
    public void approveAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setApproved(true);
        appointmentRepository.save(appointment);
    }

    @Override
    public void denyAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setApproved(false);
        appointmentRepository.save(appointment);
    }

    @Override
    public Object findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Object findByUser(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByUser'");
    }
}
