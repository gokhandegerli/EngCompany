package com.gokhan.engcompany.dto;

import com.gokhan.engcompany.enums.DepartmentType;

import java.util.List;

public class DepartmentDto {

    public int departmentIdDto;
    public EmployeeDto managerDto;
    public List<EmployeeDto> employeeDtoList;
    public List<ProjectDto> projectDtoList;
    public DepartmentType departmentTypeDto;

    public String message = "Success";

    public DepartmentDto(String message) {
        this.message = message;
    }

    public DepartmentDto () {
    }

}
