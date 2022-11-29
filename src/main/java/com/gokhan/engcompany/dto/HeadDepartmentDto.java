package com.gokhan.engcompany.dto;

import com.gokhan.engcompany.enums.DepartmentType;

import java.util.List;

public class HeadDepartmentDto {

    public int headDepartmentIdDto;
    public EmployeeDto manager;
    public List<DepartmentDto> departmentDtoList;
    public DepartmentType departmentType;

    public String message = "Success";

    public HeadDepartmentDto(String message) {
        this.message = message;
    }

    public HeadDepartmentDto () {

    }


}
