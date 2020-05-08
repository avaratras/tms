package com.hcm.tms.controller;


import com.hcm.tms.dto.SubjectCourseDto;
import com.hcm.tms.entity.Course;
import com.hcm.tms.entity.SubjectCourse;
import com.hcm.tms.service.CourseService;
import com.hcm.tms.service.SubjectCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/subjectcourse")
public class SubjectCourseController {

    @Autowired
    private SubjectCourseService subjectCourseService;

    @Autowired
    private CourseService courseService;

    @PostMapping("/api/post")
    ResponseEntity saveSubjectCourse(@RequestBody List<SubjectCourseDto> subjectCourseDtos) {
        List<SubjectCourse> subjectCourses = new ArrayList<>();
        for(SubjectCourseDto subjectCourseDto : subjectCourseDtos) {
            subjectCourses.add(subjectCourseService.save(subjectCourseDto));
        }
        if(subjectCourses.size() != 0)
            return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/api/put/{id}")
    ResponseEntity updateSubjectCourse(@PathVariable("id") Long courseId, @RequestBody List<SubjectCourseDto> subjectCourseDtos) {

        subjectCourseService.deleteAllByCourseId(courseId);


        List<SubjectCourse> subjectCourses = new ArrayList<>();
        for(SubjectCourseDto subjectCourseDto : subjectCourseDtos) {
            subjectCourses.add(subjectCourseService.save(subjectCourseDto));
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/get")
    ResponseEntity getAllSubjectCourse(@RequestParam(value = "courseid",required = false) Long courseId,
                                       @RequestParam(name = "page",required = false) Integer page,
                                       @RequestParam(name = "size",required = false) Integer pageSize) {
        if(page == null && pageSize == null) {
            return ResponseEntity.ok(subjectCourseService.findAllByCourseId(courseId));
        }
        Page<SubjectCourse> subjectCourses = subjectCourseService.findAllByCourseId(courseId,page,pageSize);
        return ResponseEntity.ok(subjectCourses);
    }
}
