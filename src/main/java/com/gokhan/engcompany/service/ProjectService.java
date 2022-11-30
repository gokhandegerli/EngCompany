package com.gokhan.engcompany.service;


import com.gokhan.engcompany.dto.ProjectDto;
import com.gokhan.engcompany.entity.Department;
import com.gokhan.engcompany.entity.Project;
import com.gokhan.engcompany.repository.ProjectRepository;
import com.gokhan.engcompany.request.ProjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
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


    public Project getProjectEntity(int projectId) {
        return repository.findById(projectId).get();
    }


    public ProjectDto createProject(ProjectRequest projectRequest) {

        checkIfProjectExists(projectRequest.name);
        Project project = new Project();
        project.setName(projectRequest.name);
        repository.save(project);
        return project.toDto();
    }

    private void checkIfProjectExists(String name) {
        if (repository.existsByName(name).get()) {
            throw new EntityExistsException();
        }
    }
}
