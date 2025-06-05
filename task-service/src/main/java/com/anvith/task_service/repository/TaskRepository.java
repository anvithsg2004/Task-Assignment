package com.anvith.task_service.repository;

import com.anvith.task_service.entity.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> { // Changed Long to String

    public List<Task> findByAssignedUserId(String userId); // Changed Long to String
}