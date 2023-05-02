package com.example.Day04Blog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    @NotEmpty(message="Username is mandatory")
    private String username;

    @Column(nullable = false, unique = true, length = 50)
    @NotEmpty (message="Email is mandatory")
    @Email
    private String email;

    @Column(nullable = false, length = 110)
    @NotEmpty(message = "Password is mandatory")
    private String password;

    @Transient
    @NotEmpty(message = "Password is mandatory")
    private String confirmPassword;
}

