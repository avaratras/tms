package com.hcm.tms.service.impl;

import com.hcm.tms.dto.CourseDto;
import com.hcm.tms.entity.Course;
import com.hcm.tms.entity.Subject;
import com.hcm.tms.entity.User;
import com.hcm.tms.repository.CourseRepository;
import com.hcm.tms.repository.SubjectRepository;
import com.hcm.tms.repository.UserRepository;
import com.hcm.tms.service.CourseService;
import com.hcm.tms.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }


    @Transactional
    @Override
    public Course save(CourseDto courseDto) {

        Course course = null;
        if(courseDto.getCourseId() != null) {
            Optional<Course> optionalCourse = courseRepository.findById(courseDto.getCourseId());
            if(optionalCourse.isPresent()) {
                course = optionalCourse.get();
            } else {
                return null;
            }
        } else {
            course = new Course();
        }
        Long duration = DAYS.between(courseDto.getStartDate(),courseDto.getEndDate());
        if(duration <= 0) return null; // 1 month
        course.setEndDate(courseDto.getEndDate());
        course.setStartDate(courseDto.getStartDate());
        course.setEnable(courseDto.isEnable());
        course.setDescription(courseDto.getDescription());
        course.setCourseName(courseDto.getCourseName());
        //List<Subject> subjects = subjectRepository.findAllById(courseDto.getSubjectId());
        //course.setSubjects(subjects);
        List<User> users = userRepository.findAllById(courseDto.getTraineeId());
        course.setTrainees(users);

        return courseRepository.save(course);

    }

    @Override
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void delete(Long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);

        optionalCourse.ifPresent(course -> {
            course.setEnable(false);
            courseRepository.save(course);
        });

    }


    @Override
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public Page<Course> findAll(Integer page, Integer pageSize) {
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
        Pageable pageable = PageRequest.of(page,pageSize, Sort.by("startDate").and(Sort.by("endDate")).ascending());
        Page<Course> courses = courseRepository.findAllByIsEnable(pageable,true);
        return courses;
    }

    @Override
    public Optional<Course> findByCourseName(String courseName) {
        return courseRepository.findByCourseNameAndIsEnable(courseName,true);
    }
}
