package com.sunday.airbnb.model;

import com.sunday.airbnb.enums.ROLE;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String fullName;
    @Enumerated(EnumType.STRING)
    private ROLE role;

    @OneToMany(mappedBy = "user")
    private List<Property> properties;
}
