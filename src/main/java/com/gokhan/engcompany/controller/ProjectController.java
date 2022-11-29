package com.gokhan.engcompany.controller;


import com.gokhan.engcompany.dto.DepartmentDto;
import com.gokhan.engcompany.dto.ProjectDto;
import com.gokhan.engcompany.request.DepartmentRequest;
import com.gokhan.engcompany.request.ProjectRequest;
import com.gokhan.engcompany.service.ClientService;
import com.gokhan.engcompany.service.EmployeeService;
import com.gokhan.engcompany.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("projects")
public class ProjectController {

    @Autowired
    ProjectService service;


    @PostMapping("create-project-with-name")
    public ProjectDto createProject(@RequestBody ProjectRequest projectRequest) {
        return service.createProject(projectRequest);
    }





}
