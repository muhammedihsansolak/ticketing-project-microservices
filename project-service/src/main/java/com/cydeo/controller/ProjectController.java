package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.dto.UserResponseDTO;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.entity.User;
import com.cydeo.exception.ProjectServiceException;
import com.cydeo.service.ProjectService;
import com.cydeo.service.UserServiceClient;
import com.cydeo.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final ProjectService projectService;
    private final UserServiceClient userServiceClient;
    private final MapperUtil mapper;

    @GetMapping
    public ResponseEntity<ResponseWrapper> getProjects(){
        List<ProjectDTO> projectDTOList = projectService.listAllProjects();
        return ResponseEntity.ok(new ResponseWrapper("Projects are successfully retrieved",projectDTOList, HttpStatus.OK));
    }

    @GetMapping("/{code}")
    public ResponseEntity<ResponseWrapper> getProjectByCode(@PathVariable("code") String code){
        ProjectDTO projectDTO = projectService.getByProjectCode(code);
        return ResponseEntity.ok(new ResponseWrapper("Project is successfully retrieved",projectDTO,HttpStatus.OK));

    }

    @PostMapping
    public ResponseEntity<ResponseWrapper> createProject(@RequestBody ProjectDTO project) throws ProjectServiceException {
        projectService.save(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("Project is successfully created",HttpStatus.CREATED));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper> updateProject(@RequestBody ProjectDTO project) throws ProjectServiceException {
        projectService.update(project);
        return ResponseEntity.ok(new ResponseWrapper("Project is successfully updated",project,HttpStatus.OK));
    }

    @DeleteMapping("/{projectCode}")
    public ResponseEntity<ResponseWrapper> deleteProject(@PathVariable("projectCode") String code) throws ProjectServiceException {
        projectService.delete(code);
        return ResponseEntity.ok(new ResponseWrapper("Project is successfully deleted",HttpStatus.OK));
    }

    @GetMapping("/details/{userName}")
    public ResponseEntity<ResponseWrapper> readAllProjectDetails(@PathVariable("userName") String userName) throws ProjectServiceException {

            List<ProjectDTO> projectDTOs = projectService.listAllProjectDetails(userName);
            return ResponseEntity.ok(new ResponseWrapper("Projects are retrieved with details",projectDTOs,HttpStatus.OK));

    }

    @PutMapping("/manager/complete/{projectCode}")
    public ResponseEntity<ResponseWrapper> managerCompleteProject(@PathVariable("projectCode") String code) throws ProjectServiceException {
        projectService.complete(code);
        return ResponseEntity.ok(new ResponseWrapper("Project is successfully completed",HttpStatus.OK));

    }

    @GetMapping("/manager/read-all-by-assigned-manager/{username}")
    public ResponseEntity<ResponseWrapper> readAllByAssignedManager(@PathVariable("username")String username){
        UserDTO userDTO = userServiceClient.getUserDTOByUserName(username).data;
        User user = mapper.convert(userDTO, new User());

        return ResponseEntity.ok(ResponseWrapper.builder()
                .success(true)
                .code(HttpStatus.OK.value())
                .message("Projects are successfully retrieved")
                .data(projectService.readAllByAssignedManager(user))
                .build()
        );
    }

}
