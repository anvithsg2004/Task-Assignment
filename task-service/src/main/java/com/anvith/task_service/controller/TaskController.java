package com.anvith.task_service.controller;

import com.anvith.task_service.entity.Task;
import com.anvith.task_service.entity.TaskStatus;
import com.anvith.task_service.entity.User;
import com.anvith.task_service.feignClient.UserServiceClient;
import com.anvith.task_service.service.TaskService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserServiceClient userServiceClient;

    @PostMapping("/create-user")
    public ResponseEntity<?> createTask(@RequestBody Task task, @RequestHeader("Authorization") String jwt) {
        try {
            User user = userServiceClient.getUserProfile(jwt);
            Task createTask = taskService.createTask(task, user.getRole());
            return new ResponseEntity<>(createTask, HttpStatus.CREATED);
        } catch (FeignException.ServiceUnavailable e) {
            return new ResponseEntity<>("User service is unavailable", HttpStatus.SERVICE_UNAVAILABLE);
        } catch (FeignException.Unauthorized e) {
            return new ResponseEntity<>("Unauthorized: Invalid or missing JWT token", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating task: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-task/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable String id, @RequestHeader("Authorization") String jwt) { // Changed Long to String
        try {
            User user = userServiceClient.getUserProfile(jwt);
            Task task = taskService.getTaskById(id);
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (FeignException.ServiceUnavailable e) {
            return new ResponseEntity<>("User service is unavailable", HttpStatus.SERVICE_UNAVAILABLE);
        } catch (FeignException.Unauthorized e) {
            return new ResponseEntity<>("Unauthorized: Invalid or missing JWT token", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving task: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/assigned-users-task/{userId}") // Changed path to use userId
    public ResponseEntity<?> assignedUsersTask(@PathVariable String userId, // Changed Long to String
                                               @RequestParam(required = false) TaskStatus status,
                                               @RequestHeader("Authorization") String jwt) {
        try {
            User user = userServiceClient.getUserProfile(jwt);
            if (!user.getId().equals(userId)) {
                return new ResponseEntity<>("Unauthorized: User ID mismatch", HttpStatus.UNAUTHORIZED);
            }
            List<Task> tasks = taskService.assignedUsersTask(userId, status);
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (FeignException.ServiceUnavailable e) {
            return new ResponseEntity<>("User service is unavailable", HttpStatus.SERVICE_UNAVAILABLE);
        } catch (FeignException.Unauthorized e) {
            return new ResponseEntity<>("Unauthorized: Invalid or missing JWT token", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving tasks: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{userId}/user/{taskId}/assigned")
    public ResponseEntity<?> assignedTaskToUser(@PathVariable String userId, @PathVariable String taskId, // Changed Long to String
                                                @RequestHeader("Authorization") String jwt) {
        try {
            User user = userServiceClient.getUserProfile(jwt);
            Task task = taskService.assignedTaskToUser(userId, taskId);
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (FeignException.ServiceUnavailable e) {
            return new ResponseEntity<>("User service is unavailable", HttpStatus.SERVICE_UNAVAILABLE);
        } catch (FeignException.Unauthorized e) {
            return new ResponseEntity<>("Unauthorized: Invalid or missing JWT token", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error assigning task: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<?> getAllTask(@RequestParam(required = false) TaskStatus status,
                                        @RequestHeader("Authorization") String jwt) {
        try {
            User user = userServiceClient.getUserProfile(jwt);
            List<Task> task = taskService.getAllTask(status);
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (FeignException.ServiceUnavailable e) {
            return new ResponseEntity<>("User service is unavailable", HttpStatus.SERVICE_UNAVAILABLE);
        } catch (FeignException.Unauthorized e) {
            return new ResponseEntity<>("Unauthorized: Invalid or missing JWT token", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving tasks: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTask(@PathVariable String id, @RequestBody Task updatedTask, // Changed Long to String
                                        @RequestHeader("Authorization") String jwt) {
        try {
            User user = userServiceClient.getUserProfile(jwt);
            Task task = taskService.updateTask(id, updatedTask, user.getId());
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (FeignException.ServiceUnavailable e) {
            return new ResponseEntity<>("User service is unavailable", HttpStatus.SERVICE_UNAVAILABLE);
        } catch (FeignException.Unauthorized e) {
            return new ResponseEntity<>("Unauthorized: Invalid or missing JWT token", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating task: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable String id, @RequestHeader("Authorization") String jwt) { // Changed Long to String
        try {
            User user = userServiceClient.getUserProfile(jwt);
            taskService.deleteTask(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (FeignException.ServiceUnavailable e) {
            return new ResponseEntity<>("User service is unavailable", HttpStatus.SERVICE_UNAVAILABLE);
        } catch (FeignException.Unauthorized e) {
            return new ResponseEntity<>("Unauthorized: Invalid or missing JWT token", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting task: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/complete/{id}")
    public ResponseEntity<?> completeTask(@PathVariable String id, @RequestHeader("Authorization") String jwt) { // Changed Long to String
        try {
            User user = userServiceClient.getUserProfile(jwt);
            Task task = taskService.completeTask(id);
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (FeignException.ServiceUnavailable e) {
            return new ResponseEntity<>("User service is unavailable", HttpStatus.SERVICE_UNAVAILABLE);
        } catch (FeignException.Unauthorized e) {
            return new ResponseEntity<>("Unauthorized: Invalid or missing JWT token", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error completing task: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
