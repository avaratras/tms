package com.hcm.tms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String userId;

    @Column(unique = true)
    @NotNull(message = "Username must not be null")
    private String username;

    @NotNull(message = "Password must not be null")
    @Size(min=8, message = "Password must be greater than 8 character")
    private String password;

    @NotNull(message = "Firstname must not be null")
    @Size(max= 15, message = "Firstname must be less than 15 character")
    private String firstname;

    @NotNull(message = "Lastname must not be null")
    @Size(min =1, max= 15, message = "Lastname must be greater than 1 and less than 20 character  ")
    private String lastname;

    @Column(unique=true)
    @NotNull(message = "Email must not be null")
    @Email(message = "Email invalid")
    private String email;

    @Past(message = "birth date must be in the past")
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @Column(unique = true)
    @NotNull(message = "Telephone must not be null")
    @Size(min=10,max= 11, message = "Telephone must be in numeric and greater than 10 and less than 11 number  ")
    private String telephone;

    @NotNull(message = "Please select city")
    @ManyToOne
    @JoinColumn(name = "cityId",nullable = false)
    private City city;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime modifiedDate;

    @Column
    private boolean isEnable;

    @Column
    private boolean isAccountNonExpired;

    @Column
    private boolean isAccountNonLocked;

    @Column
    private boolean isCredentialsNonExpired;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = { @JoinColumn(name = "userId") },
            inverseJoinColumns = {@JoinColumn(name = "roleId") })
    private Set<Role> roleList= new HashSet<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_course",
            joinColumns = { @JoinColumn(name = "userId") },
            inverseJoinColumns = {@JoinColumn(name = "courseId") })
    private List<Course> courseList = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "subject_user",
            joinColumns = { @JoinColumn(name = "userId") },
            inverseJoinColumns = {@JoinColumn(name = "subjectId")})
    private List<Subject> subjectList = new ArrayList<>();

}
