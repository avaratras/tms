package com.hcm.tms.repository;

import com.hcm.tms.entity.Course;
import com.hcm.tms.entity.Subject;
import com.hcm.tms.entity.SubjectCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectCourseRepository extends JpaRepository<SubjectCourse,Long> {
    List<SubjectCourse> findAllByCourse(Course course);
    Page<SubjectCourse> findAllByCourse(Course course,Pageable pageable);
    Optional<SubjectCourse> findByCourseAndSubject(Course course, Subject subject);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM subject_course WHERE course_id = ?1 ",nativeQuery = true)
    void deleteAllByCourseId(Long courseId);
}
