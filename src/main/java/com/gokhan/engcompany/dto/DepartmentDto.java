package com.gokhan.engcompany.dto;

import com.gokhan.engcompany.entity.Manager;
import com.gokhan.engcompany.enums.DepartmentType;

import java.util.List;

public class DepartmentDto {

    public int departmentIdDto;
    public ManagerDto managerDto;
    public List<EmployeeDto> employeeDtoList;
    public List<ProjectDto> projectDtoList;
    public DepartmentType departmentTypeDto;


}
