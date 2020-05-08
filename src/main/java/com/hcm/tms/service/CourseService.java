package com.hcm.tms.service;

import com.hcm.tms.dto.CourseDto;
import com.hcm.tms.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<Course> getAll();
    List<Course> findAll();
    Course save(CourseDto courseDto);
    Course save(Course course);
    void delete(Long id);
    Optional<Course> findById(Long id);
    Page<Course> findAll(Integer page, Integer pageSize);
    Optional<Course> findByCourseName(String courseName);
}
