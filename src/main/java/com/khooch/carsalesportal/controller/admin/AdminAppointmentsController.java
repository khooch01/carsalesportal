package com.khooch.carsalesportal.controller.admin;

import com.khooch.carsalesportal.entity.Appointment;
import com.khooch.carsalesportal.service.AppointmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AdminAppointmentsController {

    private final AppointmentService appointmentService;

    public AdminAppointmentsController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/admin/manageAppointments")
    public String listAppointments(Model model) {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        model.addAttribute("appointments", appointments);
        return "admin/manageAppointments";
    }

    @GetMapping("/admin/appointments/approve/{id}")
    public String approveAppointment(@PathVariable Long id) {
        appointmentService.approveAppointment(id);
        return "redirect:/admin/manageAppointments";
    }

    @GetMapping("/admin/appointments/deny/{id}")
    public String denyAppointment(@PathVariable Long id) {
        appointmentService.denyAppointment(id);
        return "redirect:/admin/manageAppointments";
    }
}
