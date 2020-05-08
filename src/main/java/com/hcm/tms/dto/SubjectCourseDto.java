package com.hcm.tms.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Data
public class SubjectCourseDto {

    private Long id;

    private Long subjectId;

    private Long courseId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
}
