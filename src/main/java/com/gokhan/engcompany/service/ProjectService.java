package com.gokhan.engcompany.service;


import com.gokhan.engcompany.dto.ProjectDto;
import com.gokhan.engcompany.entity.Employee;
import com.gokhan.engcompany.entity.Project;
import com.gokhan.engcompany.repository.EmployeeRepository;
import com.gokhan.engcompany.repository.ProjectRepository;
import com.gokhan.engcompany.request.ProjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    ProjectRepository repository;

    @Autowired
    EmployeeRepository employeeRepository;



    @Autowired
    ClientService clientService;


    public Project getProjectEntity(int projectId) {
        return repository.findById(projectId).get();
    }


    public ProjectDto createProject(ProjectRequest projectRequest) {

        checkIfProjectExistsWithName(projectRequest.name);
        Project project = new Project();
        project.setName(projectRequest.name);
        project.setProjectStatus(projectRequest.projectStatus);
        project.setStartDate(projectRequest.startDate);
        project = repository.save(project);
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

    public List<Employee> getProjectWithEmployees(List<Employee> allEmployees) {
        return repository.findProjectsWithEmployees(allEmployees).stream()
                .map(Project::getEmployee)
                .toList();
    }


    public List<ProjectDto> checkDateOfProject (List<Project> projectList, int dateRange) {
        LocalDate today = LocalDate.now();
        List<Project> projectsInRange = new ArrayList<>();
        for (Project project : projectList) {
            if (project.getStartDate().isBefore(today.plusDays(dateRange))) {
                projectsInRange.add(project);
            }
        }
        return projectsInRange.stream().map(Project :: toDto).toList();
    }

    public List<ProjectDto> getProjectsStartThreeMonths() {
        List<Project> allProjects = repository.findAll();
        List<ProjectDto> projectsStartThreeMonths = checkDateOfProject(allProjects,90);
        return projectsStartThreeMonths;
    }

}
