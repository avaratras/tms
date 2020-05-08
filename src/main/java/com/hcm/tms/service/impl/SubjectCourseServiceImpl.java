package com.hcm.tms.service.impl;

import com.hcm.tms.dto.SubjectCourseDto;
import com.hcm.tms.entity.Course;
import com.hcm.tms.entity.Subject;
import com.hcm.tms.entity.SubjectCourse;
import com.hcm.tms.repository.CourseRepository;
import com.hcm.tms.repository.SubjectCourseRepository;
import com.hcm.tms.repository.SubjectRepository;
import com.hcm.tms.service.SubjectCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectCourseServiceImpl implements SubjectCourseService {

    @Autowired
    private SubjectCourseRepository subjectCourseRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public SubjectCourse save(SubjectCourseDto subjectCourseDto) {
        Course course = courseRepository.findById(subjectCourseDto.getCourseId()).get();
        Subject subject = subjectRepository.findById(subjectCourseDto.getSubjectId()).get();
        Optional<SubjectCourse> optionalSubjectCourse = subjectCourseRepository.findByCourseAndSubject(course,subject);
        SubjectCourse subjectCourse = new SubjectCourse();
        if(optionalSubjectCourse.isPresent()) {
            subjectCourse.setId(optionalSubjectCourse.get().getId());
        }
        subjectCourse.setCourse(course);
        subjectCourse.setSubject(subject);
        subjectCourse.setStartDate(subjectCourseDto.getStartDate());

        subjectCourse = subjectCourseRepository.save(subjectCourse);
        return subjectCourse;
    }

    @Override
    public List<SubjectCourse> findAllByCourseId(Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if(course.isPresent()) {
            return subjectCourseRepository.findAllByCourse(course.get());
        }
        return null;
    }

    @Override
    public Page<SubjectCourse> findAllByCourseId(Long courseId, Integer page, Integer pageSize) {
        if(page == null) {
            page = 0;
        } else if(page <= 1) {
            page = 0;
        } else {
            page = page - 1;
        }
        if(pageSize == null || pageSize <= 0) {
            pageSize = 5;
        };
        Pageable pageable = PageRequest.of(page,pageSize, Sort.by("startDate").ascending());
        Optional<Course> course = courseRepository.findById(courseId);
        if(course.isPresent()) {
            return subjectCourseRepository.findAllByCourse(course.get(),pageable);
        }
        return null;

    }

    @Override
    public void deleteAllByCourseId(Long courseId) {
        subjectCourseRepository.deleteAllByCourseId(courseId);
    }
}
