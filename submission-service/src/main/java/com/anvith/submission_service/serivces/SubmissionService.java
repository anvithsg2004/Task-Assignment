package com.anvith.submission_service.serivces;

import com.anvith.submission_service.entity.Submission;
import com.anvith.submission_service.entity.Task;
import com.anvith.submission_service.fiegnClient.TaskServiceClient;
import com.anvith.submission_service.fiegnClient.UserServiceClient;
import com.anvith.submission_service.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private TaskServiceClient taskServiceClient;

    @Autowired
    private UserServiceClient userServiceClient;

    public Submission submitTask(String taskId, String GitHubLink, Long userId, String jwt) throws Exception {

        Task task = taskServiceClient.getTaskById(taskId, jwt);

        if (task != null) {
            Submission submission = new Submission();

            submission.setTaskId(taskId);
            submission.setUserId(userId);
            submission.setGitHubLink(GitHubLink);
            submission.setSubmissionTime(LocalDateTime.now());

            return submissionRepository.save(submission);
        }

        throw new Exception("Task Not Found with ID : " + taskId);

    }

    public Submission getTaskSubmissionById(Long submissionId, String jwt) throws Exception {

        return submissionRepository.findById(submissionId).orElseThrow(() -> new Exception("Task Submission Not Found with ID : " + submissionId));

    }

    public List<Submission> getAllTaskSubmission(String jwt) {

        return submissionRepository.findAll();

    }

    public List<Submission> getTaskSubmissionsByTaskId(String taskId, String jwt) {

        return submissionRepository.findByTaskId(Long.valueOf(taskId));

    }

    public Submission acceptDeclineSubmission(Long taskId, String status, String jwt) throws Exception {

        Submission submission = getTaskSubmissionById(taskId, jwt);

        submission.setStatus(status);

        if (status.equals("DONE")) {
            taskServiceClient.completeTask(submission.getTaskId(), jwt);
        }

        return submissionRepository.save(submission);

    }

}
