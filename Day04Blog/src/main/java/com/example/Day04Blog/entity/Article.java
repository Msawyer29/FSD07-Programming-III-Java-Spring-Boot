package com.example.Day04Blog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name="articles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Column(name = "creation_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime creationTime;

    @Column(nullable = false, unique = true, length = 100)
    @NotEmpty(message="Title is mandatory.")
    @Size(min=10, max=100, message="Title has to be 10-100 characters.")
    private String title;

    @Column(nullable = true, length = 4000)
    @NotEmpty(message="Content cannot be empty.")
    @Size(min=50, max=4000, message="Content must be between 50-4000 characters long.")
    private String content;

}
