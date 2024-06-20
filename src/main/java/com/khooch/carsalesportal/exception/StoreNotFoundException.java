package com.khooch.carsalesportal.exception;

public class StoreNotFoundException extends RuntimeException {

    public StoreNotFoundException(Long id) {
        super("Store not found with id : " + id);
    }
}
