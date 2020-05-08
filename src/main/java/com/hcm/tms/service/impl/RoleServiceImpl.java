package com.hcm.tms.service.impl;

import com.hcm.tms.entity.Role;
import com.hcm.tms.repository.RoleRepository;
import com.hcm.tms.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role findByRoleName(String rolename) {
        return roleRepository.findByRoleName(rolename);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public List<Role> findAllById(List<String> roleId) {
        return  roleRepository.findAllById(roleId);
    }
}
