package com.gokhan.engcompany.controller;


import com.gokhan.engcompany.dto.DepartmentDto;
import com.gokhan.engcompany.dto.HeadDepartmentDto;
import com.gokhan.engcompany.enums.DepartmentType;
import com.gokhan.engcompany.request.DepartmentRequest;
import com.gokhan.engcompany.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;

@RestController
@RequestMapping("departments")
public class DepartmentController {

    @Autowired
    DepartmentService service;

    @PostMapping()
    public DepartmentDto createDepartment(@RequestBody DepartmentRequest departmentRequest) {
        return service.createDepartment(departmentRequest);
    }
    @GetMapping("{departmentId}")
    public DepartmentDto getDepartment(@PathVariable (value="departmentId") int departmentId) {
        return service.getDepartmentDto(departmentId);
    }

    @DeleteMapping("{departmentId}")
    public String deleteDepartment(@PathVariable(name="departmentId") int departmentId) {
        return service.deleteDepartment(departmentId);
    }

    @PutMapping("{departmentId}/update-manager/{employeeId}")
    public DepartmentDto updateManager(@PathVariable (value= "employeeId") int employeeId,
                                           @PathVariable (value="departmentId") int departmentId) {
        return service.updateManager(employeeId, departmentId);
    }

    //assign/add an engineer to a team
    @PostMapping("{departmentId}/add-employee/{employeeId}")
    public DepartmentDto addDepartment (@PathVariable (value="employeeId") int employeeId,
                                        @PathVariable (value= "departmentId") int departmentId) {
        try {
            return service.addEmployee(employeeId, departmentId);
        } catch (EntityExistsException ex) {
            return new DepartmentDto("FAILED, This employee already in this department!");
        }
    }

    @PostMapping("{departmentId}/add-project/{projectId}")
    public DepartmentDto addProject (@PathVariable (value="projectId") int projectId,
                                        @PathVariable (value= "departmentId") int departmentId) {
        try {
            return service.addProject(projectId, departmentId);
        } catch (EntityExistsException ex) {
            return new DepartmentDto("FAILED, This project already a part of this department!");
        }
    }

}
