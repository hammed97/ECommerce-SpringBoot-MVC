package com.example.springmvcproject.controller;

import com.example.springmvcproject.dto.PasswordDTO;
import com.example.springmvcproject.dto.UsersDTO;
import com.example.springmvcproject.model.Users;
import com.example.springmvcproject.serviceImpl.UsersServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
@Slf4j
public class UsersController {
    private UsersServiceImpl usersService;
    @Autowired

    public UsersController(UsersServiceImpl usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/register")
    public String indexPage(Model model){
        model.addAttribute("user", new UsersDTO());
        return "sign-up";
    }
    @GetMapping("/login")
    public ModelAndView loginPage(){
       return new  ModelAndView("login").addObject(
               "user", new UsersDTO());
    }

    @PostMapping ("/sign-up")
    public String signUp(@ModelAttribute UsersDTO usersDTO){
        Users user = usersService.saveUser.apply(new Users(usersDTO));
        log.info("User details ---> {}", user);
        return "successful-register";
    }
    @PostMapping ("/login")
    public String loginUser(@ModelAttribute UsersDTO usersDTO, HttpServletRequest request, Model model){
        Users user = usersService.findUsersByUsername.apply(usersDTO.getUsername());
        log.info("User details ---> {}", user);
        if (usersService.verifyUserPassword.apply(PasswordDTO.builder().
                password(usersDTO.getPassword()).hashPassword(user.getPassword()).build())){
            HttpSession session = request.getSession();
            session.setAttribute("userID", user.getId());
            return "redirect:/products/all";

        }
        return "redirect:/user/login";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "index";
    }
}
