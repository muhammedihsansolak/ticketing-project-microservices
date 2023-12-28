package com.cydeo.service;

import com.cydeo.dto.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "user-service")
public interface UserServiceClient {

    @GetMapping("/api/v1/user/{username}")
    UserResponseDTO getUserDTOByUserName(@PathVariable("username") String username);
}
