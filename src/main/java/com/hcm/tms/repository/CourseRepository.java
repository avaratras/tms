package com.hcm.tms.repository;

import com.hcm.tms.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

    Page<Course> findAll(Pageable pageable);
    Page<Course> findAllByIsEnable(Pageable pageable,boolean isEnable);
    Optional<Course> findByCourseNameAndIsEnable(String courseName,boolean isEnable);

}
