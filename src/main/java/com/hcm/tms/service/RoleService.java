package com.hcm.tms.service;

import com.hcm.tms.entity.Role;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleService {
    Role findByRoleName(String rolename);
    List<Role> findAll();
    List<Role> findAllById(List<String> roleId);
}
