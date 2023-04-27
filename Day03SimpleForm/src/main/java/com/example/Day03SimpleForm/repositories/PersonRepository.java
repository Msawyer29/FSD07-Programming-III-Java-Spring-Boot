package com.example.Day03SimpleForm.repositories;

import com.example.Day03SimpleForm.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // provides basic CRUD operations
public interface PersonRepository extends JpaRepository <Person, Long> {
    // Interface Jpa Repository (object , primary key)
    // ex getByID(), batch delete, save a given entity, deleteById(ID, id)

    // framework convention example "findByName"
    Person findByName(String name);


    // Add manual queries (not dynamic), see reference
    // ex: @Query("SELECT f FROM Foo f WHERE LOWER(f.name) = LOWER(:name)"
    //
}