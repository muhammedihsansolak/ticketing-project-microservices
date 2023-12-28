package com.cydeo.dto;

import lombok.Data;

import java.util.List;

@Data
public class TaskResponseDTO {
    public boolean success;
    public String message;
    public int code;
    public List<TaskDTO> data;
}
