package com.khooch.carsalesportal.service;

import com.khooch.carsalesportal.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
    List<Role> findAll();
    Role findByName(String name);
}
