package com.hcm.tms.repository;

import com.hcm.tms.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,String> {
    Role findByRoleName(String name);

    Role findByRoleId(Role id);
}
