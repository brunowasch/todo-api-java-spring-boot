
package com.example.todoapi.controller;

import com.example.todoapi.entity.*;
import com.example.todoapi.repository.TaskRepository;
import com.example.todoapi.repository.UserRepository;
import com.example.todoapi.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public TaskController(TaskRepository taskRepository,
                          UserRepository userRepository,
                          JwtService jwtService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    private User getUser(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        String token = auth.replace("Bearer ", "");
        String username = jwtService.extractUsername(token);

        return userRepository.findByUsername(username).orElseThrow();
    }

    @GetMapping
    public List<Task> listar(@RequestParam(required = false) TaskStatus status,
                             HttpServletRequest request) {
        User user = getUser(request);

        if (status != null) {
            return taskRepository.findByUserAndStatus(user, status);
        }

        return taskRepository.findByUser(user);
    }

    @PostMapping
    public Task criar(@RequestBody Task task,
                       HttpServletRequest request) {
        task.setUser(getUser(request));
        return taskRepository.save(task);
    }

    @GetMapping("/{id}")
    public Task buscar(@PathVariable Long id) {
        return taskRepository.findById(id).orElseThrow();
    }

    @PatchMapping("/{id}")
    public Task atualizar(@PathVariable Long id,
                          @RequestBody Task novaTask) {

        Task task = taskRepository.findById(id).orElseThrow();

        task.setDescricao(novaTask.getDescricao());
        task.setStatus(novaTask.getStatus());

        return taskRepository.save(task);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }
}
