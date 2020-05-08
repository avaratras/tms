package com.hcm.tms.controller;


import com.hcm.tms.dto.CustomUsersDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @RequestMapping("/admin")
    public String homeAdmin() {
        return "adminPage";
    }


    @RequestMapping("/login")
    public String login(@RequestParam(required = false) String message, Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth instanceof AnonymousAuthenticationToken) { // if user not logged in
            if(message != null && !message.isEmpty()) {
                String errMessage = "";
                if (message.equals("timeout")) {
                    errMessage = "Time out!!";
                } else if (message.equals("expired")) {
                    errMessage =  "This account has been logged in from another location";
                } else if (message.equals("logout")) {
                    errMessage = "Logout success";
                } else if (message.equals("error")) {
                    errMessage = "Failed!!!";
                }
                model.addAttribute("message",errMessage);
            }
            return "login";
        }
        return "redirect:/";

    }

//    @RequestMapping(value = "/logout", method = RequestMethod.GET)
//    public RedirectView logout(HttpServletRequest request, HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if(auth != null) {
//            new SecurityContextLogoutHandler().logout(request,response,auth);
//        }
//
//        RedirectView redirectView = new RedirectView();
//        redirectView.setUrl("/login?message=logout");
//        return redirectView;
//    }

    @GetMapping("/authentication")
    public ResponseEntity getUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        CustomUsersDetails customUsersDetails = (CustomUsersDetails)auth.getPrincipal();
        return ResponseEntity.ok(customUsersDetails.getUser());
    }


    @RequestMapping("/404")
    public String denied() {

        return "page-404";
    }

    @RequestMapping("/denied-page")
    public String deniedPage() {
        return  "redirect:/404";
    }

}
