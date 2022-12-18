package com.gokhan.engcompany.controller;


import com.gokhan.engcompany.dto.DepartmentDto;
import com.gokhan.engcompany.dto.EmployeeDto;
import com.gokhan.engcompany.dto.HeadDepartmentDto;
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
        try {
            return service.updateProject(projectId, projectRequest);
        } catch (EntityExistsException ex) {
            return new ProjectDto("FAILED, This Project not exists!");
        }
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


    @PostMapping("{projectId}/add-employee/{employeeId}")
    public ProjectDto addEmployee(@PathVariable(value = "employeeId") int employeeId,
                                  @PathVariable(value = "projectId") int projectId) {
        try {
            return service.addEmployee(employeeId, projectId);
        } catch (EntityExistsException ex) {
            return new ProjectDto("FAILED, This employee already assigned to project!");
        }
        catch (NullPointerException nex){
            return new ProjectDto("FAILED, Project not exist");
        }
    }

    @PostMapping("{projectId}/remove-employee")
    public ProjectDto removeEmployee(@PathVariable(value = "projectId") int projectId) {
        try {
            return service.removeEmployee(projectId);
        } catch (EntityExistsException ex) {
            return new ProjectDto("FAILED, Project not exist ");
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
        catch (NullPointerException nex){
            return new ProjectDto("FAILED, Project not exist");
        }
    }

    @PostMapping("{projectId}/remove-manager")
    public ProjectDto removeManager(@PathVariable(value = "projectId") int projectId) {
        try {
            return service.removeManager(projectId);
        } catch (EntityExistsException ex) {
            return new ProjectDto("FAILED, Project not exist ");
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
        catch (NullPointerException nex){
            return new ProjectDto("FAILED, Project not exist");
        }
    }

    @PostMapping("{projectId}/remove-client")
    public ProjectDto removeClient(@PathVariable(value = "projectId") int projectId) {
        try {
            return service.removeClient(projectId);
        } catch (EntityExistsException ex) {
            return new ProjectDto("FAILED, Project not exist ");
        }
    }


}
