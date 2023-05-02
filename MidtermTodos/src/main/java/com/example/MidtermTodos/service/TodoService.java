package com.example.MidtermTodos.service;

import com.example.MidtermTodos.model.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoService {
    List<Todo> getAllTodos();
    Optional<Todo> getTodoById(int id);
    Todo saveTodo(Todo todo);
    void deleteTodoById(int id);
}
