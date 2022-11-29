package com.gokhan.engcompany.controller;


import com.gokhan.engcompany.dto.DepartmentDto;
import com.gokhan.engcompany.dto.HeadDepartmentDto;
import com.gokhan.engcompany.enums.DepartmentType;
import com.gokhan.engcompany.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;

@RestController
@RequestMapping("departments")
public class DepartmentController {

    @Autowired
    DepartmentService service;

    @PostMapping("create-department-with-name")
    public DepartmentDto createDepartment(@RequestBody DepartmentType departmentType) {
        return service.createDepartment(departmentType);
    }
    @GetMapping("{departmentId}")
    public DepartmentDto getDepartment(@PathVariable (value="departmentId") int departmentId) {
        return service.getDepartment(departmentId);
    }

    @DeleteMapping("{departmentId}")
    public String deleteDepartment(@PathVariable(name="departmentId") int departmentId) {
        return service.deleteDepartment(departmentId);
    }

    @PutMapping("update-manager/{employeeId}/{departmentId}")
    public DepartmentDto updateManager(@PathVariable (value= "employeeId") int employeeId,
                                           @PathVariable (value="departmentId") int departmentId) {
        return service.updateManager(employeeId, departmentId);
    }

    @PostMapping("add-employee/{employeeId}/{departmentId}")
    public DepartmentDto addDepartment (@PathVariable (value="departmentId") int employeeId,
                                            @PathVariable (value= "departmentId") int departmentId) {

        try {
            return service.addEmployee(employeeId, departmentId);
        } catch (EntityExistsException ex) {
            return new DepartmentDto("FAILED, This employee already in this department!");
        }
    }

}
