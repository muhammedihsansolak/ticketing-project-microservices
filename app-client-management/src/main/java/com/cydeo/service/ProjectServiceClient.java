package com.cydeo.service;

import com.cydeo.dto.ProjectResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "project-service")
public interface ProjectServiceClient {

    @GetMapping("/api/v1/project/manager/read-all-by-assigned-manager/{username}")
    ProjectResponseDTO readAllByAssignedManager(@PathVariable("username")String username);

}
