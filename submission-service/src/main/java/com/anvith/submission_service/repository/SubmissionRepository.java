package com.anvith.submission_service.repository;

import com.anvith.submission_service.entity.Submission;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SubmissionRepository extends MongoRepository<Submission, String> { // Changed Long to String
    List<Submission> findByTaskId(String taskId);
}
