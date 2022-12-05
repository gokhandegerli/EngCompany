package com.gokhan.engcompany.service;


import com.gokhan.engcompany.dto.ProjectDto;
import com.gokhan.engcompany.entity.Project;
import com.gokhan.engcompany.repository.ProjectRepository;
import com.gokhan.engcompany.request.ProjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;

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

        checkIfProjectExistsWithName(projectRequest.name);
        Project project = new Project();
        project.setName(projectRequest.name);
        repository.save(project);
        return project.toDto();
    }

    private void checkIfProjectExistsWithName(String name) {
        if (repository.existsByName(name)) {
            throw new EntityExistsException();
        }
    }

    protected boolean checkIfProjectExists(int projectId ) {
        if (repository.existsByProjectId(projectId)) {
            return true;
        } else {
            throw new EntityExistsException();
        }
    }

    public String deleteProject(int projectId) {
        if(getProjectEntity(projectId)!= null) {
            if(getProjectEntity(projectId).getDepartment()==null){
                repository.deleteById(projectId);
                return "Proje silindi";
            } else {
                return "Projeyi önce ilgili departmandan siliniz!";
            }
        } else {
            return "Proje silinemedi, çünkü yok";
        }
    }

    public ProjectDto getProjectDto(int projectId) {
        return getProjectEntity(projectId).toDto();
    }
}
