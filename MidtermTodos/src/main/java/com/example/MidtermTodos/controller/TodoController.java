package com.example.MidtermTodos.controller;

import com.example.MidtermTodos.model.Todo;
import com.example.MidtermTodos.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public String listTodos(Model model) {
        List<Todo> todos = todoService.getAllTodos();
        model.addAttribute("todos", todos);
        return "list-todos";
    }

    @GetMapping("/add")
    public String showAddTodoForm(@ModelAttribute("todo") Todo todo) {
        return "add-todo";
    }

    @PostMapping("/add")
    public String processAddTodoForm(@ModelAttribute("todo") @Valid Todo todo, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-todo";
        }
        todoService.saveTodo(todo);
        return "redirect:/todos";
    }

    @GetMapping("/edit/{id}")
    public String showEditTodoForm(@PathVariable("id") int id, Model model) {
        Todo todo = todoService.getTodoById(id).orElseThrow(() -> new IllegalArgumentException("Invalid todo Id:" + id));
        model.addAttribute("todo", todo);
        return "edit-todo";
    }

    @PostMapping("/edit/{id}")
    public String editTodo(@PathVariable("id") int id, @Valid Todo todo, BindingResult result, Model model) {
        if (result.hasErrors()) {
            todo.setId(id);
            model.addAttribute("errorMessage", "Please correct the errors in the form.");
            return "edit-todo";
        }
        todoService.saveTodo(todo);
        return "redirect:/todos";
    }

    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable("id") int id, Model model) {
        Todo todo = todoService.getTodoById(id).orElseThrow(() -> new IllegalArgumentException("Invalid todo Id:" + id));
        todoService.deleteTodoById(id);
        return "redirect:/todos";
    }
}
