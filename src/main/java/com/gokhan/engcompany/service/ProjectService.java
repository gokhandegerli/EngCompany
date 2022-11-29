package com.gokhan.engcompany.service;


import com.gokhan.engcompany.dto.ProjectDto;
import com.gokhan.engcompany.entity.Project;
import com.gokhan.engcompany.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    ProjectRepository repository;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    ClientService clientService;


    public List<ProjectDto> getProjectDtoList(List<Project> projectList) {
        List<ProjectDto> projectDtoList = new ArrayList<>();

        for (Project project:projectList) {
            projectDtoList.add(toDto(project));
        }
         return projectDtoList;
    }

    private ProjectDto toDto(Project project) {
        ProjectDto dto = new ProjectDto();
        dto.projectIdDto = project.getProjectId();
        dto.nameDto = project.getName();
        dto.employeeDto =employeeService.getEmployeeDto(project.getEmployee());
        dto.managerDto = project.getManager().toDto();
        dto.clientDto = clientService.getClientDto(project.getClient());
       return dto;
    }



}
