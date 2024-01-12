package com.example.springmvcproject.controller;

import com.example.springmvcproject.dto.AdminDTO;
import com.example.springmvcproject.dto.PasswordDTO;
import com.example.springmvcproject.model.Admin;
import com.example.springmvcproject.serviceImpl.AdminServiceImpl;
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
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    private AdminServiceImpl adminService;

    @Autowired
    public AdminController(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/admin-register")
    public String signUpPage(Model model){
        model.addAttribute("admin", new AdminDTO());
        return "admin-signup";
    }

    @GetMapping("/admin-login")
    public ModelAndView loginPage(){
        return  new ModelAndView("admin-login")
                .addObject("admin", new AdminDTO());
    }

    @PostMapping("/admin-signup")
    public String signUp(@ModelAttribute AdminDTO adminDto){
        Admin admin = adminService.saveAdmin.apply(new Admin(adminDto));
        log.info("Admin details ---> {}", admin);
        return "admin-successful-register";
    }

    @PostMapping("/admin-login")
    public String loginUser(@ModelAttribute AdminDTO adminDto, HttpServletRequest request, Model model){
        Admin admin = adminService.findAdminByUsername.apply(adminDto.getUsername());
        log.info("Admin details ---> {}", admin);
        if (adminService.verifyAdminPassword
                .apply(PasswordDTO.builder()
                        .password(adminDto.getPassword())
                        .hashPassword(admin.getPassword())
                        .build())){
            HttpSession session = request.getSession();
            session.setAttribute("adminID", admin.getId());
            return "redirect:/products/admin/all";
        }
        return "redirect:/admin/admin-login";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "index";
    }
}
