package com.gokhan.engcompany.controller;


import com.gokhan.engcompany.dto.DepartmentDto;
import com.gokhan.engcompany.dto.EmployeeDto;
import com.gokhan.engcompany.dto.HeadDepartmentDto;
import com.gokhan.engcompany.dto.ProjectDto;
import com.gokhan.engcompany.entity.Department;
import com.gokhan.engcompany.enums.DepartmentType;
import com.gokhan.engcompany.request.DepartmentRequest;
import com.gokhan.engcompany.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import java.util.List;

@RestController
@RequestMapping("departments")
public class DepartmentController {

    @Autowired
    DepartmentService service;

    @PostMapping()
    public DepartmentDto createDepartment(@RequestBody DepartmentRequest departmentRequest) {
        return service.createDepartment(departmentRequest);
    }

    @PutMapping("{departmentId}")
    public DepartmentDto updateDepartment(@PathVariable(value = "departmentId") int departmentId,
                                          @RequestBody DepartmentRequest departmentRequest) {
        try {
            return service.updateDepartment(departmentId, departmentRequest);
        } catch (NullPointerException nex) {
            return new DepartmentDto("FAILED, This department not exist");
        }
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
        catch (NullPointerException nex){
            return new DepartmentDto("FAILED, Department not exist");
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
        catch (NullPointerException nex){
            return new DepartmentDto("FAILED, Department not exist");
        }
    }

    @PostMapping("{departmentId}/remove-employee/{employeeId}")
    public DepartmentDto removeEmployee(@PathVariable(value = "employeeId") int employeeId,
                                        @PathVariable(value = "departmentId") int departmentId) {
        try {
            return service.removeEmployee(employeeId, departmentId);
        } catch (EntityExistsException ex) {
            return new DepartmentDto("FAILED, Department not exist");
        }
    }

    @PostMapping("{departmentId}/remove-manager/{managerId}")
    public DepartmentDto removeManager(@PathVariable(value = "managerId") int managerId,
                                       @PathVariable(value = "departmentId") int departmentId) {
        try {
            return service.removeManager(managerId, departmentId);
        } catch (EntityExistsException ex) {
            return new DepartmentDto("FAILED, Department not exist");
        }
    }

    @PostMapping("{departmentId}/remove-project/{projectId}")
    public DepartmentDto removeProject(@PathVariable(value = "projectId") int projectId,
                                       @PathVariable(value = "departmentId") int departmentId) {
        try {
            return service.removeProject(projectId, departmentId);
        } catch (EntityExistsException ex) {
            return new DepartmentDto("FAILED, Department not exist or" +
                    " this project is not a part of this department!");
        }
    }

    @GetMapping("/{departmentId}/employees")
    public List<EmployeeDto> getADepartmentEmployees(@PathVariable(value = "departmentId") int departmentId) {
        return service.getADepartmentEmployees(departmentId);
    }

    @GetMapping("{departmentId}/projects")
    public List<ProjectDto> getADepartmentProjects(@PathVariable(value = "departmentId") int departmentId) {
        return service.getADepartmentProjects(departmentId);
    }

}
