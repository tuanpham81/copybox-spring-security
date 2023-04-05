package com.copybox.demo.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String userName;
    private String password;
//    private String userRole;
//    private String phoneNumber;
    private String email;
    private String refLink;
    private String refCode;
    private double balance;
    private double totalEarning;
    @DBRef
    private Set<Role> roles = new HashSet<>();
}
