package com.gokhan.engcompany.dto;

import com.gokhan.engcompany.enums.ProjectStatus;

import java.time.LocalDate;
import java.util.Date;

public class ProjectDto {

    public int projectIdDto;
    public String nameDto;
    public ClientDto clientDto;
    public EmployeeDto employeeDto;
    public EmployeeDto managerDto;
    public DepartmentDto departmentDto;
    public ProjectStatus projectStatus;
    public LocalDate startDate;

    public String message = "Success";

    public ProjectDto (String message) {
        this.message = message;
    }

    public ProjectDto () {
    }


}
