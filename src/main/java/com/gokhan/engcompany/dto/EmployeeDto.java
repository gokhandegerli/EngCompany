package com.gokhan.engcompany.dto;

import com.gokhan.engcompany.enums.Title;

public class EmployeeDto {

    public int employeeIdDto;
    public PersonDto personDto;
    public Title titleDto;
    public boolean isManager;

    public String message = "Success";

    public EmployeeDto (String message) {
        this.message = message;
    }

    public EmployeeDto () {
    }


}
