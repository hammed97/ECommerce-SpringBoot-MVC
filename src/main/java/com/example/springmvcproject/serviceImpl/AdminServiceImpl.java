package com.example.springmvcproject.serviceImpl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.springmvcproject.dto.PasswordDTO;
import com.example.springmvcproject.model.Admin;
import com.example.springmvcproject.repositories.AdminRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AdminServiceImpl {
    private AdminRepositories adminRepositories;

    @Autowired
    public AdminServiceImpl(AdminRepositories adminRepositories) {
        this.adminRepositories = adminRepositories;
    }

    public Function<String, Admin> findAdminByUsername = (username)->
            adminRepositories.findByUsername(username)
                    .orElseThrow(()->new NullPointerException("Admin not found!"));
    public Function<Long, Admin> findAdminById = (id)->
            adminRepositories.findById(id)
                    .orElseThrow(()->new NullPointerException("User not found!"));

    public Function<Admin, Admin> saveAdmin = (admin)->adminRepositories.save(admin);

    public Function<PasswordDTO, Boolean> verifyAdminPassword = passwordDTO -> BCrypt.verifyer()
            .verify(passwordDTO.getPassword().toCharArray(),
                    passwordDTO.getHashPassword().toCharArray())
            .verified;
}
