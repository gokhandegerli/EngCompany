package com.gokhan.engcompany.controller;


import com.gokhan.engcompany.dto.DepartmentDto;
import com.gokhan.engcompany.dto.EmployeeDto;
import com.gokhan.engcompany.dto.ProjectDto;
import com.gokhan.engcompany.request.DepartmentRequest;
import com.gokhan.engcompany.request.ProjectRequest;
import com.gokhan.engcompany.service.ClientService;
import com.gokhan.engcompany.service.EmployeeService;
import com.gokhan.engcompany.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import java.util.List;

@RestController
@RequestMapping("projects")
public class ProjectController {

    @Autowired
    ProjectService service;


    @PostMapping()
    public ProjectDto createProject(@RequestBody ProjectRequest projectRequest) {
        return service.createProject(projectRequest);
    }

    @PutMapping("{projectId}")
    public ProjectDto updateProject(@PathVariable(value = "projectId") int projectId,
                                    @RequestBody ProjectRequest projectRequest) {
        return service.updateProject(projectId, projectRequest);
    }

    @GetMapping("{projectId}")
    public ProjectDto getProject(@PathVariable(value = "projectId") int projectId) {
        return service.getProjectDto(projectId);
    }

    @DeleteMapping("{projectId}")
    public String deleteProject(@PathVariable(value = "projectId") int projectId) {
        return service.deleteProject(projectId);
    }

    @GetMapping("get-projects-start-within-3-months")
    public List<ProjectDto> getProjectsStartThreeMonths() {
        return service.getProjectsStartThreeMonths();
    }

    @GetMapping()
    public List<ProjectDto> getAllProject() {
        return service.getAllProjects();
    }

    @GetMapping("{departmentId}/get-a-department-projects")
    public List<ProjectDto> getADepartmentProjects(@PathVariable(value = "departmentId") int departmentId) {
        return service.getADepartmentProjects(departmentId);
    }

    @PostMapping("{projectId}/add-employee/{employeeId}")
    public ProjectDto addEmployee(@PathVariable(value = "employeeId") int employeeId,
                                  @PathVariable(value = "projectId") int projectId) {
        try {
            return service.addEmployee(employeeId, projectId);
        } catch (EntityExistsException ex) {
            return new ProjectDto("FAILED, This employee already assigned to project!");
        }
    }

    @PostMapping("{projectId}/remove-employee/{employeeId}/delete-employee/{deleteEmployee}")
    public ProjectDto removeEmployee(@PathVariable(value = "employeeId") int employeeId,
                                     @PathVariable(value = "projectId") int projectId,
                                     @PathVariable(value = "deleteEmployee") boolean deleteEmployee) {
        try {
            return service.removeEmployee(employeeId, projectId, deleteEmployee);
        } catch (EntityExistsException ex) {
            return new ProjectDto("FAILED, Project not exist or " +
                    "this employee is not asssigned to this project!");
        }
    }

    @PostMapping("{projectId}/add-manager/{employeeId}")
    public ProjectDto addManager(@PathVariable(value = "employeeId") int employeeId,
                                 @PathVariable(value = "projectId") int projectId) {
        try {
            return service.addManager(employeeId, projectId);
        } catch (EntityExistsException ex) {
            return new ProjectDto("FAILED, This manager already assigned to project!");
        }
    }

    @PostMapping("{projectId}/remove-manager/{employeeId}/delete-manager/{deleteManager}")
    public ProjectDto removeManager(@PathVariable(value = "employeeId") int employeeId,
                                    @PathVariable(value = "projectId") int projectId,
                                    @PathVariable(value = "deleteManager") boolean deleteManager) {
        try {
            return service.removeManager(employeeId, projectId, deleteManager);
        } catch (EntityExistsException ex) {
            return new ProjectDto("FAILED, Project not exist or " +
                    "this manager is not assigned to this project!");
        }
    }

    @PostMapping("{projectId}/add-client/{clientId}")
    public ProjectDto addClient(@PathVariable(value = "clientId") int clientId,
                                @PathVariable(value = "projectId") int projectId) {
        try {
            return service.addClient(clientId, projectId);
        } catch (EntityExistsException ex) {
            return new ProjectDto("FAILED, This client already assigned to project!");
        }
    }

    @PostMapping("{projectId}/remove-client/{clientId}/delete-client/{deleteClient}")
    public ProjectDto removeClient(@PathVariable(value = "clientId") int clientId,
                                    @PathVariable(value = "projectId") int projectId,
                                    @PathVariable(value = "deleteClient") boolean deleteClient) {
        try {
            return service.removeClient(clientId, projectId, deleteClient);
        } catch (EntityExistsException ex) {
            return new ProjectDto("FAILED, Project not exist or " +
                    "this client is not assigned to this project!");
        }
    }


}
