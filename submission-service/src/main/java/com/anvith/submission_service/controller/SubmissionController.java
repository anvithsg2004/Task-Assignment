package com.anvith.submission_service.controller;

import com.anvith.submission_service.entity.Submission;
import com.anvith.submission_service.entity.User;
import com.anvith.submission_service.fiegnClient.TaskServiceClient;
import com.anvith.submission_service.fiegnClient.UserServiceClient;
import com.anvith.submission_service.serivces.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submission")
public class SubmissionController {

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private TaskServiceClient taskServiceClient;

    @Autowired
    private SubmissionService submissionService;

    @PostMapping("/submit-task")
    public ResponseEntity<Submission> submitTask(
            @RequestParam Long taskId,
            @RequestParam String gitHubLink,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        User user = userServiceClient.getUserProfile(jwt);

        Submission submission = submissionService.submitTask(String.valueOf(taskId), gitHubLink, Long.parseLong(user.getId()), jwt);

        return new ResponseEntity<>(submission, HttpStatus.CREATED);

    }

    @GetMapping("/get-all-submissions")
    public ResponseEntity<List<Submission>> getAllSubmissions(@RequestHeader("Authorization") String jwt) throws Exception {

        User user = userServiceClient.getUserProfile(jwt);

        List<Submission> submissions = submissionService.getAllTaskSubmission(jwt);

        return new ResponseEntity<>(submissions, HttpStatus.OK);

    }

    @GetMapping("/get-task-submissions-by-task-id/{taskId}")
    public ResponseEntity<List<Submission>> getTaskSubmissionsByTaskId(@PathVariable Long taskId, @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userServiceClient.getUserProfile(jwt);

        List<Submission> submissions = submissionService.getTaskSubmissionsByTaskId(String.valueOf(taskId), jwt);

        return new ResponseEntity<>(submissions, HttpStatus.OK);

    }

    @PutMapping("/accept-decline-submission")
    public ResponseEntity<Submission> acceptDeclineSubmission(@PathVariable Long taskId, @RequestParam("status") String status, @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userServiceClient.getUserProfile(jwt);

        Submission submission = submissionService.acceptDeclineSubmission(taskId, status, jwt);

        return new ResponseEntity<>(submission, HttpStatus.OK);

    }

}
