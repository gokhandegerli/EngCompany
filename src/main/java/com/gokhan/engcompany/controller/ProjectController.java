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

    @GetMapping("{projectId}")
    public ProjectDto getProject(@PathVariable (value="projectId") int projectId) {
        return service.getProjectDto(projectId);
    }

    @DeleteMapping("{projectId}/delete-project")
    public String deleteProject(@PathVariable (value="projectId") int projectId) {
        return service.deleteProject(projectId);
    }

    @GetMapping("get-projects-start-within-3-months")
    public List<ProjectDto> getProjectsStartThreeMonths(){
        return service.getProjectsStartThreeMonths();
    }





}
