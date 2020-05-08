package com.hcm.tms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data

public class Role {
    @Id
    private String roleId;

    @NotNull
    @Size(max= 30,message = "Role name must be less than 30 character")
    private String roleName;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = { @JoinColumn(name = "roleId") },
            inverseJoinColumns = {@JoinColumn(name = "userId") })
    private List<User> userList = new ArrayList<>();


    public Role(String roleName) {
        this.roleId = UUID.randomUUID().toString();
        this.roleName = roleName;
    }

    public Role() {
    }
}
