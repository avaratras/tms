package com.hcm.tms.controller;

import com.hcm.tms.entity.City;
import com.hcm.tms.entity.Role;
import com.hcm.tms.entity.User;
import com.hcm.tms.repository.RoleRepository;
import com.hcm.tms.repository.UserRepository;
import com.hcm.tms.service.CityService;
import com.hcm.tms.service.RoleService;
import com.hcm.tms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/admin/trainee")
public class FresherController {

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CityService cityService;

    @Autowired
    private UserService userService;

    @RequestMapping("/all")
    public List getAll(Model model, @RequestParam("page")Optional<Integer> page) {
        return userService.getAllFreshers();
    }

    @GetMapping("/get")
    public ModelAndView getFreshersAndPaging(@RequestParam("page")Optional<Integer> page,
                                             @RequestParam("name") String name) {
        int currentPage = page.filter(i -> i > 0).orElse(1);

        List<Integer> listPages = new ArrayList<>();
        List<User> list = new ArrayList<>();
        Page<User> pages = userService.getFreshersAndPaging(name, currentPage - 1);

        if(pages.hasContent()) {
            list = pages.toList();
            listPages = userService.getPages(pages);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("fresherList");
        modelAndView.addObject("fresherList",list);
        modelAndView.addObject("listPages", listPages);
        modelAndView.addObject("currentPage", currentPage);

        return modelAndView;
    }

    @GetMapping(value = "/getFromAddFresher")
    public ModelAndView insertTrainer(Model model){
        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("fresher",new User());
        model.addAttribute("cities", cityService.findAll());
        modelAndView.setViewName("form-add-fresher");
        return modelAndView;
    }


    @PostMapping(value = "/add-fresher")
    public ModelAndView saveTrainer(@Valid @ModelAttribute("fresher") User fresher, BindingResult error, Model model) {

        ModelAndView modelAndView = new ModelAndView();
        if (error.hasErrors()) {
            model.addAttribute("cities", cityService.findAll());
            modelAndView.setViewName("form-add-fresher");

        } else {

            fresher.setPassword(passwordEncoder.encode(fresher.getPassword()));
            fresher.setCreatedDate(LocalDateTime.now());
            fresher.setAccountNonExpired(true);
            fresher.setAccountNonLocked(true);
            fresher.setCredentialsNonExpired(true);
            fresher.setEnable(true);
            fresher.getRoleList().add(roleService.findByRoleName("TRAINEE"));

            userService.addUser(fresher);

            modelAndView.setViewName("redirect:/admin/trainee/get?page=&name=");

        }
        return modelAndView;
    }

    @GetMapping("/edit-fresher/{id}")
    public ModelAndView updateFresherPage(Model model, @PathVariable("id") String id) {
        Optional<User> fresher = userService.getUserById(id);

        ModelAndView modelAndView = new ModelAndView();
        if (fresher.isPresent()) {
            model.addAttribute("cities", cityService.findAll());
            model.addAttribute("roles", roleService.findAll());

            model.addAttribute("fresher", fresher.get());
            modelAndView.setViewName("form-edit-fresher");
            return modelAndView;
        }
        else modelAndView.setViewName("redirect:/admin/trainee/add-fresher");
        return modelAndView;
    }

    @PostMapping("/edit-fresher")
    public ModelAndView updateFresher(@Valid @ModelAttribute("fresher") User fresher, BindingResult error, @RequestParam(value = "role", required = false) List<String> roleId, Model model) {
        ModelAndView modelAndView = new ModelAndView();

        if (error.hasErrors()) {
            model.addAttribute("cities", cityService.findAll());
            modelAndView.setViewName("");

        } else {
            if (roleId!=null && !roleService.findAllById(roleId).isEmpty()) {
                for (Role role : roleService.findAllById(roleId)) {
                    fresher.getRoleList().add(role);
                }
            }
            fresher.setAccountNonExpired(true);
            fresher.setAccountNonLocked(true);
            fresher.setCredentialsNonExpired(true);
            fresher.setPassword(passwordEncoder.encode(fresher.getPassword()));
            fresher.getRoleList().add(roleService.findByRoleName("TRAINEE"));
            fresher.setModifiedDate(LocalDateTime.now());
            fresher.setEnable(true);
            userService.addUser(fresher);
            modelAndView.setViewName("redirect:/admin/trainee/get?page=&name=");
        }
        return modelAndView;
    }





    @GetMapping("/api/get")
    public ResponseEntity getTrainers() {
        List<User> users = userService.findAllByRoleName("TRAINEE");
        return ResponseEntity.ok(users);
    }

    @RequestMapping("/delete/{id}")
    public void deleteFresher(@PathVariable String id) {
        userService.delete(id);

    }
}
