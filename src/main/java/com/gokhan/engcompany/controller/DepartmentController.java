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
    public DepartmentDto getDepartment(@PathVariable(value = "departmentId") int departmentId) {
        return service.getDepartmentDto(departmentId);
    }

    @DeleteMapping("{departmentId}")
    public String deleteDepartment(@PathVariable(name = "departmentId") int departmentId) {
        return service.deleteDepartment(departmentId);
    }

    @PutMapping("{departmentId}/update-manager/{employeeId}")
    public DepartmentDto updateManager(@PathVariable(value = "employeeId") int employeeId,
                                       @PathVariable(value = "departmentId") int departmentId) {
        return service.updateManager(employeeId, departmentId);
    }

    //assign/add an engineer to a team
    @PostMapping("{departmentId}/add-employee/{employeeId}")
    public DepartmentDto addEmployee(@PathVariable(value = "employeeId") int employeeId,
                                     @PathVariable(value = "departmentId") int departmentId) {
        try {
            return service.addEmployee(employeeId, departmentId);
        } catch (EntityExistsException ex) {
            return new DepartmentDto("FAILED, This employee already in this department!");
        }
    }

    @PostMapping("{departmentId}/add-project/{projectId}")
    public DepartmentDto addProject(@PathVariable(value = "projectId") int projectId,
                                    @PathVariable(value = "departmentId") int departmentId) {
        try {
            return service.addProject(projectId, departmentId);
        } catch (EntityExistsException ex) {
            return new DepartmentDto("FAILED, This project already a part of this department!");
        }
    }

    @PostMapping("{departmentId}/remove-employee/{employeeId}/delete-employee/{deleteEmployee}")
    public DepartmentDto removeEmployee(@PathVariable(value = "employeeId") int employeeId,
                                        @PathVariable(value = "departmentId") int departmentId,
                                        @PathVariable(value = "deleteEmployee") boolean deleteEmployee) {
        try {
            return service.removeEmployee(employeeId, departmentId,deleteEmployee);
        } catch (EntityExistsException ex) {
            return new DepartmentDto("FAILED, Department not exist or " +
                    "this employee is not a part of this department!");
        }
    }

    @PostMapping("{departmentId}/remove-manager/{managerId}/delete-manager/{deleteManager}")
    public DepartmentDto removeManager(@PathVariable(value = "managerId") int managerId,
                                        @PathVariable(value = "departmentId") int departmentId,
                                        @PathVariable(value = "deleteManager") boolean deleteManager) {
        try {
            return service.removeManager(managerId, departmentId,deleteManager);
        } catch (EntityExistsException ex) {
            return new DepartmentDto("FAILED, Department not exist or " +
                    "this manager is not a part of this department!");
        }
    }

    @PostMapping("{departmentId}/remove-project/{projectId}/delete-manager/{deleteProject}")
    public DepartmentDto removeProject(@PathVariable(value = "projectId") int projectId,
                                       @PathVariable(value = "departmentId") int departmentId,
                                       @PathVariable(value = "deleteProject") boolean deleteProject) {
        try {
            return service.removeProject(projectId, departmentId, deleteProject);
        } catch (EntityExistsException ex) {
            return new DepartmentDto("FAILED, Department not exist or" +
                    " this project is not a part of this department!");
        }
    }


}
