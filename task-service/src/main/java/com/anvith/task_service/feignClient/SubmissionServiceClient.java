package com.anvith.task_service.feignClient;

import com.anvith.task_service.entity.Submission;
import com.anvith.task_service.entity.SubmissionComment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "SUBMISSION-SERVICE", path = "/api/submission")
public interface SubmissionServiceClient {

    @PostMapping("/submit-task")
    Submission submitTask(
            @RequestParam String taskId,
            @RequestParam String gitHubLink,
            @RequestHeader("Authorization") String jwt
    );

    @GetMapping("/get-all-submissions")
    Page<Submission> getAllSubmissions(
            @RequestHeader("Authorization") String jwt,
            Pageable pageable
    );

    @GetMapping("/get-task-submissions-by-task-id/{taskId}")
    Page<Submission> getTaskSubmissionsByTaskId(
            @PathVariable("taskId") String taskId,
            @RequestHeader("Authorization") String jwt,
            Pageable pageable
    );

    @PutMapping("/accept-decline-submission/{submissionId}")
    Submission acceptDeclineSubmission(
            @PathVariable("submissionId") String submissionId,
            @RequestParam("status") String status,
            @RequestHeader("Authorization") String jwt
    );

    @PostMapping("/comment/{submissionId}")
    SubmissionComment addComment(
            @PathVariable("submissionId") String submissionId,
            @RequestParam("comment") String comment,
            @RequestHeader("Authorization") String jwt
    );

    @GetMapping("/comments/{submissionId}")
    List<SubmissionComment> getCommentsBySubmissionId(
            @PathVariable("submissionId") String submissionId,
            @RequestHeader("Authorization") String jwt
    );

    @GetMapping("/accepted-task-ids/{userId}")
    List<String> getAcceptedTaskIdsForUser(
            @PathVariable("userId") String userId,
            @RequestHeader("Authorization") String jwt
    );
}
