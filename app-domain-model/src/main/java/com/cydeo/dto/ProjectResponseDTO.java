package com.cydeo.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProjectResponseDTO {
    public boolean success;
    public String message;
    public int code;
    public List<ProjectDTO> data;
}
