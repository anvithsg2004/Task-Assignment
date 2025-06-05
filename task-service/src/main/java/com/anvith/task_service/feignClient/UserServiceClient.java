package com.anvith.task_service.feignClient;

import com.anvith.task_service.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "USER-SERVICE", path = "/api/user")
public interface UserServiceClient {

    @GetMapping("/profile")
    User getUserProfile(@RequestHeader("Authorization") String authHeader);

    @GetMapping("/all")
    List<User> getAllUsers();
}
