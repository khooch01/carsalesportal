package com.khooch.carsalesportal.service.impl;

import com.khooch.carsalesportal.entity.Role;
import com.khooch.carsalesportal.repository.RoleRepository;
import com.khooch.carsalesportal.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findByName(String name) {
        Optional<Role> optionalRole = roleRepository.findByName(name);
        return optionalRole.orElse(null); // Or throw exception or handle as needed
    }
}
