package com.example.springmvcproject.model;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.springmvcproject.dto.AdminDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String imageUrl;
    private String password;
    private String fullName;
    private BigDecimal balance;


    public Admin(AdminDTO adminDto) {
        this.username = adminDto.getUsername();
        this.password =  BCrypt.withDefaults()
                .hashToString(12, adminDto.getPassword().toCharArray());
        this.fullName = adminDto.getFullName();
        this.balance = new BigDecimal(2500000);
    }
}
