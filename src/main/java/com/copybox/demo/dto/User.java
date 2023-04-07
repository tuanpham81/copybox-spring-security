package com.copybox.demo.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@RequiredArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;
//    private String phoneNumber;
    private String email;
    private String password;
    private String refLink;
//    private String refCode;
    private double balance;
    private double totalEarning;
    @DBRef
    private Set<Role> roles = new HashSet<>();

    public User(String username, String email, String password, String refLink, double balance, double totalEarning) {
        this.username = username;
        this.email = email;
        this.refLink = refLink;
        this.password = password;
        this.balance = balance;
        this.totalEarning = totalEarning;
    }
}
