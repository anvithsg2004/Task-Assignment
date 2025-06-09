package com.anvith.submission_service.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Task {

    private String id;
    private String title;
    private String description;
    private String image;
    private String assignedUserId;
    private List<String> tags = new ArrayList<>();
    private TaskStatus status;
    private LocalDateTime deadline;
    private LocalDateTime createdAt;

    public Task() {
    }

    public Task(String id, String title, String description, String image, String assignedUserId,
                List<String> tags, TaskStatus status, LocalDateTime deadline, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.assignedUserId = assignedUserId;
        this.tags = tags;
        this.status = status;
        this.deadline = deadline;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(String assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
