package com.example.springmvcproject.repositories;

import com.example.springmvcproject.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

    public interface AdminRepositories extends JpaRepository<Admin, Long> {
        Optional<Admin> findByUsername(String username);
    }
