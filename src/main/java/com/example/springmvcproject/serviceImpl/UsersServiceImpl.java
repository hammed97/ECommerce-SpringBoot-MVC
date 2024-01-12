package com.example.springmvcproject.serviceImpl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.springmvcproject.dto.PasswordDTO;
import com.example.springmvcproject.model.Users;
import com.example.springmvcproject.repositories.UsersRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UsersServiceImpl {
    private UsersRepositories usersRepositories;

    @Autowired
    public UsersServiceImpl(UsersRepositories usersRepositories) {
        this.usersRepositories = usersRepositories;
    }
    public Function<String, Users> findUsersByUsername = (username)->
        usersRepositories.findByUsername(username).orElseThrow(()->new NullPointerException("User not found"));
    public Function<Long, Users> findUsersById = (id)->
            usersRepositories.findById(id)
                    .orElseThrow(()->new NullPointerException("User not found!"));
    public Function<Users, Users> saveUser = (user)-> usersRepositories.save(user);
    public Function<PasswordDTO, Boolean> verifyUserPassword = passwordDTo -> BCrypt.verifyer().verify(passwordDTo.getPassword().
            toCharArray(), passwordDTo.getHashPassword().toCharArray()).verified;

}
