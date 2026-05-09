
package com.example.todoapi.repository;

import com.example.todoapi.entity.Task;
import com.example.todoapi.entity.TaskStatus;
import com.example.todoapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
    List<Task> findByUserAndStatus(User user, TaskStatus status);
}
