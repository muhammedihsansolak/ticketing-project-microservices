package com.cydeo.service;

import com.cydeo.dto.TaskResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "task-service")
public interface TaskServiceClient {

    @GetMapping("/api/v1/task/manager/read-all-by-assigned-manager/{username}")
    TaskResponseDTO readAllByEmployee (@PathVariable("username")String username);

}
