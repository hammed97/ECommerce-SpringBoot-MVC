package com.example.springmvcproject.repositories;

import com.example.springmvcproject.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepositories extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);
}
