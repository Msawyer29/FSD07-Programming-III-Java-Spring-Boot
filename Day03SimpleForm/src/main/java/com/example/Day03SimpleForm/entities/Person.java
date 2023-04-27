package com.example.Day03SimpleForm.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Entity
// @Table(name="people") // recommended to use
@Data
// @AllArgsConstructor // review class video, why was this commented out?
@NoArgsConstructor // you can have your own constructor but still need this
public class Person {

    // Fields
    @Id // say explicitly this is primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Validation annotations
    @NotNull(message = "Name is required") // javax.validation (added validation-api dependency to pom.xml v2.0.1.Final)
    @Size(min = 1, max = 20, message = "Name must be between 1 and 20 characters")
    private String name;

    @NotNull // not null in database
    @Min(value = 0, message = "Age must be greater than or equal to 0")
    @Max(value = 150, message = "Age must be less than or equal to 150")
    private int age;

    // Constructor
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
