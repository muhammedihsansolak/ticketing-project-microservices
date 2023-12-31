package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.exception.UserServiceException;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.ProjectServiceClient;
import com.cydeo.service.TaskServiceClient;
import com.cydeo.service.UserService;
import com.cydeo.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ProjectServiceClient projectServiceClient;
    private final TaskServiceClient taskServiceClient;
    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;


    @Override
    public List<UserDTO> listAllUsers() {
        List<User> list = userRepository.findAll(Sort.by("firstName"));
        return list.stream().map(obj -> mapperUtil.convert(obj,new UserDTO())).collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String username) throws AccessDeniedException {
        User user = userRepository.findByUserName(username);
        return mapperUtil.convert(user,new UserDTO());
    }

    @Override
    public UserDTO save(UserDTO dto) throws UserServiceException {

        User foundUser = userRepository.findByUserName(dto.getUserName());

        if(foundUser!=null){
            throw new UserServiceException("User already exists");
        }

        User user =  mapperUtil.convert(dto,new User());

        User save = userRepository.save(user);

        return mapperUtil.convert(save,new UserDTO());

    }

    @Override
    public UserDTO update(UserDTO dto) throws UserServiceException, AccessDeniedException {

        //Find current user
        User user = userRepository.findByUserName(dto.getUserName());

        if(user == null){
            throw new UserServiceException("User Does Not Exists");
        }
        //Map update user dto to entity object
        User convertedUser = mapperUtil.convert(dto,new User());

        convertedUser.setEnabled(true);

        //set id to the converted object
        convertedUser.setId(user.getId());
        //save updated user
        userRepository.save(convertedUser);

        return findByUserName(dto.getUserName());
    }

    @Override
    public void delete(String username) throws UserServiceException {
        User user = userRepository.findByUserName(username);

        if(user == null){
            throw new UserServiceException("User Does Not Exists");
        }

        if(!checkIfUserCanBeDeleted(user)){
            throw new RuntimeException("User can not be deleted. It is linked by a project or task");
        }

        user.setUserName(user.getUserName() + "-" + user.getId());

        user.setIsDeleted(true);
        userRepository.save(user);
    }

    //hard delete
    @Override
    public void deleteByUserName(String username) {
        userRepository.deleteByUserName(username);
    }


    @Override
    public List<UserDTO> listAllByRole(String role) {
        List<User> users = userRepository.findAllByRoleDescriptionIgnoreCase(role);
        return users.stream().map(obj -> {return mapperUtil.convert(obj,new UserDTO());}).collect(Collectors.toList());
    }

    private Boolean checkIfUserCanBeDeleted(User user) {
        String userName = user.getUserName();

        switch(user.getRole().getDescription()){
            case "Manager":
                List<ProjectDTO> projectList = projectServiceClient.readAllByAssignedManager(userName).data;
                return projectList.size() == 0;
            case "Employee":
                List<TaskDTO> taskList = taskServiceClient.readAllByEmployee(userName).data;
                return taskList.size() == 0;
            default:
                return true;
        }
    }

    @Override
    public UserDTO confirm(User user) {

        user.setEnabled(true);
        User confirmedUser = userRepository.save(user);

        return mapperUtil.convert(confirmedUser,new UserDTO());
    }


}
