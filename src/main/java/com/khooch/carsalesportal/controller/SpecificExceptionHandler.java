package com.khooch.carsalesportal.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.khooch.carsalesportal.exception.StoreNotFoundException;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class SpecificExceptionHandler {

    @ExceptionHandler(StoreNotFoundException.class)
    public String handleStoreNotFoundException(StoreNotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "view_stores";
    }
}
