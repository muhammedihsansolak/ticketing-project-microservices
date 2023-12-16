package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.dto.UserResponseDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;

import com.cydeo.exception.ProjectServiceException;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.UserClientService;
import com.cydeo.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;
    private MapperUtil mapperUtil;
    private UserClientService userClientService;

    public ProjectServiceImpl(ProjectRepository projectRepository, MapperUtil mapperUtil, UserClientService userClientService) {
        this.projectRepository = projectRepository;
        this.mapperUtil = mapperUtil;
        this.userClientService = userClientService;
    }

    @Override
    public ProjectDTO getByProjectCode(String code) {
        Project project = projectRepository.findByProjectCode(code);
        return mapperUtil.convert(project,new ProjectDTO());
    }

    @Override
    public List<ProjectDTO> listAllProjects() {

        List<Project> list = projectRepository.findAll();
        return list.stream().map(obj -> mapperUtil.convert(obj,new ProjectDTO())).collect(Collectors.toList());

    }

    @Override
    public ProjectDTO save(ProjectDTO dto) throws ProjectServiceException {

        Project foundProject = projectRepository.findByProjectCode(dto.getProjectCode());

        if(foundProject != null){
            throw new ProjectServiceException("Project with this code already existing");
        }

        Project obj = mapperUtil.convert(dto,new Project());

        Project createdProject = projectRepository.save(obj);

        return mapperUtil.convert(createdProject,new ProjectDTO());

    }

    @Override
    public ProjectDTO update(ProjectDTO dto) throws ProjectServiceException {

        Project project = projectRepository.findByProjectCode(dto.getProjectCode());

        if(project == null){
            throw new ProjectServiceException("Project does not exist");
        }

        Project convertedProject = mapperUtil.convert(dto,new Project());

        Project updatedProject = projectRepository.save(convertedProject);

        return mapperUtil.convert(updatedProject,new ProjectDTO());

    }


    @Override
    public void delete(String code) {
        Project project = projectRepository.findByProjectCode(code);

        project.setIsDeleted(true);
        project.setProjectCode(project.getProjectCode() + "-" + project.getId());

        projectRepository.save(project);

//        taskService.deleteByProject(projectMapper.convertToDto(project));

    }

    @Override
    public ProjectDTO complete(String projectCode) {

        Project project = projectRepository.findByProjectCode(projectCode);
        project.setProjectStatus(Status.COMPLETE);

        Project completedProject= projectRepository.save(project);

        return mapperUtil.convert(completedProject,new ProjectDTO());

//        taskService.completeByProject(projectMapper.convertToDto(project));
    }

    @Override
    public List<ProjectDTO> listAllProjectDetails(String userName) throws ProjectServiceException {

        UserResponseDTO userResponseDto = userClientService.getUserDTOByUserName(userName);

        UserDTO user = userResponseDto.getData();

        if(user != null){
            List<Project> list = projectRepository.findAllByAssignedManagerId(user.getId());

            if(list.size() == 0 ){
                throw new ProjectServiceException("This manager does not have any project assigned");
            }

            return list.stream().map(project -> {
                ProjectDTO projectDTO = new ProjectDTO();
                projectDTO.setProjectDetail(project.getProjectDetail());
                projectDTO.setProjectStatus(project.getProjectStatus());
                projectDTO.setProjectName(project.getProjectName());
                projectDTO.setProjectCode(project.getProjectCode());
                projectDTO.setAssignedManager(user);
                return projectDTO;
            }).collect(Collectors.toList());
        }
        throw new ProjectServiceException("user couldn't find");
    }


    @Override
    public List<ProjectDTO> readAllByAssignedManager(User user) {
        List<Project> list = projectRepository.findAllByAssignedManager(user);
        return list.stream().map(obj ->mapperUtil.convert(obj,new ProjectDTO())).collect(Collectors.toList());
    }

    @Override
    public List<ProjectDTO> listAllNonCompletedProjects() {

        return projectRepository.findAllByProjectStatusIsNot(Status.COMPLETE)
                .stream()
                .map(project -> mapperUtil.convert(project,new ProjectDTO()))
                .collect(Collectors.toList());
    }


}
