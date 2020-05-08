package com.hcm.tms.service;

import com.hcm.tms.dto.SubjectCourseDto;
import com.hcm.tms.entity.Course;
import com.hcm.tms.entity.SubjectCourse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SubjectCourseService {
    SubjectCourse save(SubjectCourseDto subjectCourseDto);
    List<SubjectCourse> findAllByCourseId(Long courseId);

    Page<SubjectCourse> findAllByCourseId(Long courseId,Integer page, Integer pageSize);
    void deleteAllByCourseId(Long courseId);
}
