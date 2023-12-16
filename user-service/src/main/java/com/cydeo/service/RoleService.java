package com.cydeo.service;

import com.cydeo.dto.RoleDTO;
import com.cydeo.exception.UserServiceException;

import java.util.List;

public interface RoleService {

    List<RoleDTO> listAllRoles();
    RoleDTO findById(Long id) throws UserServiceException;
}
