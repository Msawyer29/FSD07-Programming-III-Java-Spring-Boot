package com.example.thymeleafdemo;

public class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    //GETTERS
    public String getName(){
        return name;
    }
    public int getAge(){
        return age;
    }
}
