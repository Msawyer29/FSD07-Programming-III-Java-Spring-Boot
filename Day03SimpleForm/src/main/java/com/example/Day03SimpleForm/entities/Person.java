package com.example.Day03SimpleForm.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;

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

    @NotNull(message = "Age is required") // not null in database
    @Min(value = 0, message = "Age must be greater than or equal to 0")
    @Max(value = 150, message = "Age must be less than or equal to 150")
    private Integer age; // Change from 'int' to 'Integer'

    // Constructor
    public Person(String name, Integer age) { // Change from 'int' to 'Integer'
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

    public Integer getAge() { // Change from 'int' to 'Integer'
        return age;
    }

    public void setAge(Integer age) { // Change from 'int' to 'Integer'
        this.age = age;
    }
}
