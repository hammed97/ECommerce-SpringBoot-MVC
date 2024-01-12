package com.example.springmvcproject.repositories;

import com.example.springmvcproject.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepositories extends JpaRepository<Order, Long> {

}
