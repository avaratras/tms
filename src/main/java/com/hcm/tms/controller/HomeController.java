package com.hcm.tms.controller;

import com.hcm.tms.entity.User;
import com.hcm.tms.repository.UserRepository;
import com.hcm.tms.service.UserService;
import com.hcm.tms.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String homePage(){
        return "redirect:/admin/courses/lists";
    }

    @GetMapping("/trainer")
    public List<User> getAllTrainer(){
       return userService.findAllTrainer();
    }

   /* @RequestMapping(value = "/addTrainer",method = RequestMethod.POST)
    public String insertFresher(@Valid User fresher, BindResult result, Model model){
         userService.insertFresher(fresher);
        return "";
    }*/

}
