package com.gokhan.engcompany.controller;


import com.gokhan.engcompany.dto.DepartmentDto;
import com.gokhan.engcompany.enums.DepartmentType;
import com.gokhan.engcompany.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("departments")
public class DepartmentController {

    @Autowired
    DepartmentService service;

    @PostMapping("create-department-with-name")
    public DepartmentDto createDepartment(@RequestBody DepartmentType departmentType) {
        return service.createDepartment(departmentType);
    }

    @DeleteMapping("{DepartmentId}")
    public String deleteDepartment(@PathVariable(name="DepartmentId") int DepartmentId) {
        return service.deleteDepartment(DepartmentId);
    }

}
