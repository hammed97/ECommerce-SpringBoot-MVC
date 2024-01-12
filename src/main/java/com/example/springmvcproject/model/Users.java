package com.example.springmvcproject.model;

import com.example.springmvcproject.dto.UsersDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import at.favre.lib.crypto.bcrypt.BCrypt;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String imageUrl;
    private String password;
    private String fullName;
    private BigDecimal balance;

    public Users(UsersDTO usersDTO) {
        this.username = usersDTO.getUsername();
        this.password = BCrypt.withDefaults().hashToString(12,usersDTO.getPassword().toCharArray());
        this.fullName = usersDTO.getFullName();
        this.balance = new BigDecimal(2500000);
    }
}
