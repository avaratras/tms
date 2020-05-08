package com.hcm.tms.controller;

import com.hcm.tms.entity.User;
import com.hcm.tms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hcm.tms.entity.City;
import com.hcm.tms.entity.Role;
import com.hcm.tms.entity.User;
import com.hcm.tms.service.CityService;
import com.hcm.tms.service.RoleService;
import com.hcm.tms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class TrainerController {
    @Autowired
    private UserService userService;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    private CityService cityService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "admin/getAllTrainer",method = RequestMethod.GET)
    public String getAllTrainer(Model model){
        List<User> trainers = userService.findAllTrainer();
        model.addAttribute("trainers",trainers);
        return "listTrainer";
    }

    @GetMapping("admin/add-trainer")
    public String addTrainerPage(Model model) {
        model.addAttribute("trainer", new User());
        model.addAttribute("cities", cityService.findAll());
        return "form-add-trainer";
    }

    @PostMapping("admin/add-trainer")
    public String saveTrainer(@Valid @ModelAttribute("trainer") User trainer, BindingResult error, Model model) {
        if (error.hasErrors()) {
            model.addAttribute("cities", cityService.findAll());
            return "form-add-trainer";
        } else {
            trainer.setUserId(UUID.randomUUID().toString());
            trainer.setPassword(passwordEncoder.encode(trainer.getPassword()));
            trainer.setCreatedDate(LocalDateTime.now());
            trainer.setAccountNonExpired(true);
            trainer.setAccountNonLocked(true);
            trainer.setCredentialsNonExpired(true);
            trainer.setEnable(true);
            trainer.getRoleList().add(roleService.findByRoleName("TRAINER"));
         //   trainer.getRoleList().add(roleService.findByRoleName("TRAINEE"));
            userService.addUser(trainer);
            return "redirect:/admin/getAllTrainer";
        }
    }

    @GetMapping("admin/edit-trainer/{id}")
    public String updateTrainerPage(Model model, @PathVariable("id") String id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            model.addAttribute("cities", cityService.findAll());
            model.addAttribute("roles", roleService.findAll());
            model.addAttribute("trainer", user.get());
            return "form-edit-trainer";
        }
        return "forward:/admin/add-trainer";
    }

    @PostMapping("admin/edit-trainer")
    public String updateTrainer(@Valid @ModelAttribute("trainer") User trainer, BindingResult error,
                                @RequestParam(value = "role", required = false) List<String> roleId, Model model) {
        if (error.hasErrors()) {
            model.addAttribute("cities", cityService.findAll());
            return "form-edit-trainer";
        } else {
//            if (roleId!=null && !roleService.findAllById(roleId).isEmpty()) {
//                for (Role role : roleService.findAllById(roleId)) {
//                    trainer.getRoleList().add(role);
//                }
//            }
            trainer.setAccountNonExpired(true);
            trainer.setAccountNonLocked(true);
            trainer.setCredentialsNonExpired(true);
            trainer.setEnable(true);
            trainer.getRoleList().add(roleService.findByRoleName("TRAINER"));
            //trainer.setModifiedDate(LocalDateTime.now());
            userService.addUser(trainer);
            return "redirect:/admin/getAllTrainer";
        }
    }

    @GetMapping("admin/check-exist-user")
    @ResponseBody
    public String checkExistUser(HttpServletRequest request) {
        String username = request.getParameter("username");
        String userId = request.getParameter("userId");
        if (userId == null) {
            if (userService.findUserByUsername(username).isPresent()) {
                return "true";
            } else {
                return "false";
            }
        }
        else {
            if (userService.findUserByUserIdAndUsername(userId,username).isPresent()) {
                return "true";
            } else {
                return "false";
            }
        }
    }

    @GetMapping("admin/check-exist-email")
    @ResponseBody
    public String checkExistEmail(HttpServletRequest request){
        String email= request.getParameter("email");
        String userId = request.getParameter("userId");
        if (userId==null) {
            if (userService.findUserByEmail(email).isPresent()) {
                return "true";
            } else {
                return "false";
            }
        }else {
            if (userService.findUserByUserIdAndEmail(userId,email).isPresent()) {
                return "true";
            } else {
                return "false";
            }
        }
    }

    @GetMapping("admin/check-exist-phonenumber")
    @ResponseBody
    public String checkExistPhonenumber(HttpServletRequest request) {
        String phonenumber = request.getParameter("phonenumber");
        String userId = request.getParameter("userId");
        if (userId==null){
            if (userService.findUserByTel(phonenumber).isPresent()) {
                return "true";
            } else {

                return "false";
            }
        }else{
            if (userService.findUserByUserIdAndTelephone(userId,phonenumber).isPresent()) {
                return "true";
            } else {

                return "false";
            }
        }
    }

    @RequestMapping("admin/delete-trainer/{id}")
    public void deleteById(@PathVariable String id) {
        userService.deleteTrainer(LocalDate.now(),id);
    }

    @GetMapping("/admin/check/{id}")
    public ResponseEntity checkId(@PathVariable String id) {
        if(userService.getUserById(id).isPresent())
            return ResponseEntity.ok("true");
        else
            return ResponseEntity.ok("false");
    }

    @GetMapping("admin/trainer/get/{id}")
    public ResponseEntity findById(@PathVariable String id) {
        User trainer = userService.getUserById(id).get();

        return ResponseEntity.ok(trainer);
    }
}
