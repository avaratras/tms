package com.hcm.tms.controller;


import com.hcm.tms.dto.CourseDto;
import com.hcm.tms.entity.Course;
import com.hcm.tms.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin/courses")
public class CourseController {



    @Autowired
    private CourseService courseService;

    @GetMapping("/g")
    public ResponseEntity getAllCourses(@RequestParam(name = "page",required = false) Integer page, @RequestParam(name = "size",required = false) Integer pageSize) {

        return ResponseEntity.ok(courseService.findAll(page,pageSize));
    }

    @GetMapping("/api/get")
    public ResponseEntity getCourse(@RequestParam(value = "name",required = false) String courseName,
                                    @RequestParam(value = "id",required = false) Long courseId) {
        Optional<Course> optionalCourse = Optional.empty();
        if(courseName != null) {
            optionalCourse = courseService.findByCourseName(courseName);
        } else if (courseId != null) {
            optionalCourse = courseService.findById(courseId);
        }
        return ResponseEntity.ok(optionalCourse);


    }

    @PostMapping("/api/post")
    public ResponseEntity createCourse(@RequestBody CourseDto courseDto, BindingResult result) {

        if(result.hasErrors()) {
            return ResponseEntity.notFound().build();
        }
        Course course = courseService.save(courseDto);

        if(course == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(course);
        }

    }

    @PutMapping("/api/put/{id}")
    public ResponseEntity updateCourse(@PathVariable("id") Long id,@RequestBody CourseDto courseDto, BindingResult result) {
        if(result.hasErrors()) {
            return ResponseEntity.notFound().build();
        }
        courseDto.setCourseId(id);
        Course  course = courseService.save(courseDto);
        if(course == null) {
            return ResponseEntity.notFound().build();
        } else  {
            return ResponseEntity.ok(course);
        }

    }

    @DeleteMapping("/api/delete/{id}")
    public ResponseEntity deleteCourse(@PathVariable("id") Long id) {
        courseService.delete(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping("/lists")
    public String listCourse() {
        return "courseList";
    }

    @RequestMapping("/create")
    public String createCourseForm() {
        return "course-create";
    }

    @RequestMapping("/edit")
    public String viewCourse() {
        return "course-edit";
    }


}
