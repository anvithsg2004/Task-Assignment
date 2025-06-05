package com.anvith.task_service.service;

import com.anvith.task_service.entity.Task;
import com.anvith.task_service.entity.TaskStatus;
import com.anvith.task_service.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(Task task, String requestRole) throws Exception {
        if (!requestRole.equals("ROLE_ADMIN")) {
            throw new Exception("Only Admin can create the class.");
        }
        task.setStatus(TaskStatus.PENDING);
        task.setCreatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public Task getTaskById(String id) throws Exception { // Changed Long to String
        return taskRepository.findById(id).orElseThrow(() -> new Exception("Task Not Found with ID: " + id));
    }

    public List<Task> getAllTask(TaskStatus status) {
        List<Task> allTask = taskRepository.findAll();
        List<Task> filteredTasks = allTask.stream().filter(
                task -> status == null || task.getStatus().name().equalsIgnoreCase(status.toString())
        ).toList();
        return filteredTasks;
    }

    public Task updateTask(String id, Task updatedTask, String userId) throws Exception { // Changed Long to String
        Task existingTask = getTaskById(id);
        if (updatedTask.getTitle() != null) {
            existingTask.setTitle(updatedTask.getTitle());
        }
        if (updatedTask.getImage() != null) {
            existingTask.setImage(updatedTask.getImage());
        }
        if (updatedTask.getDescription() != null) {
            existingTask.setDescription(updatedTask.getDescription());
        }
        if (updatedTask.getStatus() != null) {
            existingTask.setStatus(updatedTask.getStatus());
        }
        if (updatedTask.getDeadline() != null) {
            existingTask.setDeadline(updatedTask.getDeadline());
        }
        return taskRepository.save(existingTask);
    }

    public void deleteTask(String id) throws Exception { // Changed Long to String
        getTaskById(id);
        taskRepository.deleteById(id);
    }

    public Task assignedTaskToUser(String userId, String taskId) throws Exception { // Changed Long to String
        Task task = getTaskById(taskId);
        task.setAssignedUserId(userId);
        task.setStatus(TaskStatus.DONE);
        return taskRepository.save(task);
    }

    public List<Task> assignedUsersTask(String userId, TaskStatus status) { // Changed Long to String
        List<Task> allTasks = taskRepository.findByAssignedUserId(userId);
        List<Task> filteredTasks = allTasks.stream().filter(
                task -> status == null || task.getStatus().name().equalsIgnoreCase(status.toString())
        ).toList();
        return filteredTasks;
    }

    public Task completeTask(String taskId) throws Exception { // Changed Long to String
        Task task = getTaskById(taskId);
        task.setStatus(TaskStatus.DONE);
        return taskRepository.save(task);
    }
}
