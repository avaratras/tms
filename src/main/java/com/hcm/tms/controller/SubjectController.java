package com.hcm.tms.controller;

import com.hcm.tms.entity.Course;
import com.hcm.tms.entity.Subject;
import com.hcm.tms.entity.SubjectCourse;
import com.hcm.tms.entity.User;
import com.hcm.tms.repository.CategoryRepository;
import com.hcm.tms.service.*;
import com.hcm.tms.validation.Unique;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @RequestMapping("/get/all")
    public String getAll(@RequestParam("page")Optional<Integer> page,
                         @RequestParam("title") String title, Model model) {
        int currentPage = page.filter(i -> i > 0).orElse(1);
        List<Subject> subjectList = new ArrayList<>();
        List<Integer> listPages = new ArrayList<>();
        Page<Subject> pages = subjectService.getAll(title,currentPage-1);

        if(pages.hasContent()) {
            subjectList = pages.toList();
            listPages = subjectService.getPages(pages);
        }

        model.addAttribute("subjectList", subjectList);
        model.addAttribute("listPages", listPages);
        model.addAttribute("currentPage", currentPage);

        return "subject";
    }

    /*@RequestMapping("/paging/subjects")
    public ResponseEntity getAllForPaging(@RequestParam("page")Optional<Integer> page,
                                          @RequestParam("title") String title) {
        int currentPage = page.filter(i -> i > 0).orElse(1);
        return ResponseEntity.ok(subjectService.getAll(title,currentPage-1));
    }*/

    @RequestMapping("/get/subjects")
    public ResponseEntity getSubject() {
        return ResponseEntity.ok(subjectService.getSubjects());
    }

    @GetMapping(value = "/create")
    public String createForm(Model model) {
        model.addAttribute("subject", new Subject());
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("courses", courseService.getAll());
        model.addAttribute("trainers", userService.findAllTrainer());
        return "subjectForm";
    }

    @PostMapping(value = "/create")
    public String addForm(@ModelAttribute("subject") @Valid Subject subject, BindingResult error, Model model) {

        if (error.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            model.addAttribute("trainers", userService.findAllTrainer());
            return "subjectForm";
        } else {
            try {
                List<User> trainers = new ArrayList<>();
                for (User trainer : subject.getTrainer()) {
                    if (userService.getUserById(trainer.getUserId()).isPresent() && trainer.isEnable() == true)
                        trainers.add(userService.getUserById(trainer.getUserId()).get());
                }
                subject.setTrainer(trainers);
                subjectService.save(subject);
            } catch (Exception e) {System.out.println("error at save: " + e.toString());}
        }
        return "redirect:/admin/subject/get/all?page=&title=";
    }

    @GetMapping(value = "/edit/{id}")
    public String editForm(@PathVariable("id") String id, Model model) {
        Optional<Subject> subject = subjectService.findById(Long.parseLong(id));
        if(subject.isPresent()) {
            model.addAttribute("categories", categoryService.getAll());
            model.addAttribute("trainers", userService.findAllTrainer());
            model.addAttribute("subject", subject.get());
            if(subject.get().getTrainer().size() >0) {
                List<User> trainers = new ArrayList<>();
                for (User trainer : subject.get().getTrainer()) {
                    if(trainer.isEnable() == true)
                        trainers.add(trainer);
                }
                subject.get().setTrainer(trainers);
            }
        }
        return "subjectForm";
    }

    @RequestMapping("/delete/{id}")
    public ResponseEntity deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.ok("Delete Completed");
    }

    @GetMapping("/check/{title}")
    public ResponseEntity checkIfExist(@PathVariable String title) {

        if (subjectService.findByTitle(title).isPresent())
            return ResponseEntity.ok("true");
        else
            return ResponseEntity.ok("false");
    }

    @GetMapping("/api/get")
    ResponseEntity getAllSubjects(@RequestParam(name = "page",required = false) Integer page, @RequestParam(name = "size",required = false) Integer pageSize) {

        Page<Subject> subjects = subjectService.findAll(page,pageSize);
        return ResponseEntity.ok(subjects);
    }
}