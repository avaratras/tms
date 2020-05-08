package com.hcm.tms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcm.tms.service.SubjectService;
import com.hcm.tms.validation.Unique;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Unique(service =  SubjectService.class, fieldName = "title", message = "title is already exist")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subjectId;

    @NotNull(message = "Title must not be null")
    private String title;

    private String description;

    /*@NotNull(message = "Start date must not be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;*/

    @NotNull(message = "Duration must not be null")
    @Min(value = 1, message = "Duration in range of 1 and 40 hours")
    @Max(value = 40, message = "Duration in range of 1 and 40 hours")
    private int duration;

    @NotNull(message = "Please select a Category")
    @ManyToOne
    @JoinColumn(name = "categoryId", referencedColumnName = "categoryId")
    private Category category;

    /*@NotNull(message = "Please select Courses")
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "subject_course",
            joinColumns = { @JoinColumn(name = "subjectId") },
            inverseJoinColumns = {@JoinColumn(name = "courseId")})
    private List<Course> listCourse = new ArrayList<>();*/

    @NotNull(message = "Please select Courses")
    @OneToMany(mappedBy = "subject", orphanRemoval = true)
    @JsonIgnore
    private List<SubjectCourse> subjectCourses = new ArrayList<>();

    /*@NotNull(message = "Please select Trainers")*/
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "subject_user",
            joinColumns = { @JoinColumn(name = "subjectId") },
            inverseJoinColumns = {@JoinColumn(name = "userId")})
    private List<User> trainer = new ArrayList<>();

    /*@NotNull(message = "Documentation link must not be null")*/
    private String documentationLink;

    private boolean isEnable = true;
}
