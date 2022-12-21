package com.gokhan.engcompany.service;


import com.gokhan.engcompany.dto.ProjectDto;
import com.gokhan.engcompany.entity.Client;
import com.gokhan.engcompany.entity.Department;
import com.gokhan.engcompany.entity.Employee;
import com.gokhan.engcompany.entity.Project;
import com.gokhan.engcompany.repository.ClientRepository;
import com.gokhan.engcompany.repository.EmployeeRepository;
import com.gokhan.engcompany.repository.ProjectRepository;
import com.gokhan.engcompany.request.ProjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.util.*;

@Service
public class ProjectService {

    @Autowired
    ProjectRepository repository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ClientRepository clientRepository;


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

    public String deleteProject(int projectId) {
        if (getProjectEntity(projectId) != null) {
            if (getProjectEntity(projectId).getDepartment() == null) {
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


    public List<ProjectDto> checkDateOfProject(List<Project> projectList, int dateRange) {
        LocalDate today = LocalDate.now();
        return projectList.stream() // alternatif, stream şeklinde olan.
                .filter(project -> project.getStartDate().isBefore(today.plusDays(dateRange)))
                .map(Project::toDto)
                .toList();

/*        List<Project> projectsInRange = new ArrayList<>();
        for (Project project : projectList) {
            if (project.getStartDate().isBefore(today.plusDays(dateRange))) {
                projectsInRange.add(project);
            }
        }
        return projectsInRange.stream().map(Project::toDto).toList();*/
    }

    public List<ProjectDto> getProjectsStartThreeMonths() {
        List<Project> allProjects = repository.findAll();
        List<ProjectDto> projectsStartThreeMonths = checkDateOfProject(allProjects, 90);
        return projectsStartThreeMonths;
    }

    public ProjectDto updateProject(int projectId, ProjectRequest projectRequest) {

        Optional<Project> project = repository.findById(projectId);
        if (project.isPresent()) {
            project.get().setName(projectRequest.name);
            project.get().setProjectStatus(projectRequest.projectStatus);
            project.get().setStartDate(projectRequest.startDate);
            return repository.save(project.get()).toDto();
        }
        throw new EntityExistsException();
    }

    public List<ProjectDto> getAllProjects() {
        return repository.findAll().stream().map(Project::toDto).toList();
    }


    public ProjectDto addEmployee(int employeeId, int projectId) {

        Optional<Project> project = repository.findById(projectId);
        checkIfEmployeeNotAssignedProject(project
                .orElseThrow(() -> new NullPointerException()),employeeId);
        Employee employee = employeeRepository.findById(employeeId).get();
        project.get().setEmployee(employee);
        return repository.save(project.get()).toDto();
    }

    private void checkIfEmployeeNotAssignedProject(Project project, int employeeId) {

        boolean alreadyExist = false;
        if (project.getEmployee() != null) {
            alreadyExist = project.getEmployee().getEmployeeId() == employeeId;
        }
        if (alreadyExist) {
            throw new EntityExistsException();
        }
    }

    public ProjectDto removeEmployee(int projectId) {

        if (repository.existsByProjectId(projectId)) {
            Project project = repository.findById(projectId).get();
            project.setEmployee(null);
            return (repository.save(project).toDto());
        } else {
            throw new EntityExistsException();
        }
    }


    public ProjectDto addManager(int managerId, int projectId) {

        Optional<Project> project = repository.findById(projectId);
        checkIfManagerNotAssignedProject(project
                .orElseThrow(() -> new NullPointerException()),managerId);
        Employee manager = employeeRepository.findById(managerId).get();
        project.get().setManager(manager);
        return repository.save(project.get()).toDto();
    }

    private void checkIfManagerNotAssignedProject(Project project, int managerId) {

        boolean alreadyExist = false;
        if (project.getManager() != null) {
            alreadyExist = project.getManager().getEmployeeId() == managerId;
        }
        if (alreadyExist) {
            throw new EntityExistsException();
        }
    }

    public ProjectDto removeManager(int projectId) {

        if (repository.existsByProjectId(projectId)) {
            Project project = repository.findById(projectId).get();
            project.setManager(null);
            return (repository.save(project).toDto());
        } else {
            throw new EntityExistsException();
        }
    }

    public ProjectDto addClient(int clientId, int projectId) {

        Optional<Project> project = repository.findById(projectId);
        checkIfClientNotAssignedProject(project
                .orElseThrow(() -> new NullPointerException()),clientId);
        Client client = clientRepository.findById(clientId).get();
        project.get().setClient(client);
        return repository.save(project.get()).toDto();
    }

    private void checkIfClientNotAssignedProject(Project project, int clientId) {
        boolean alreadyExist = false;
        if (project.getClient() != null) {
            alreadyExist = project.getClient().getClientId() == clientId;
        }
        if (alreadyExist) {
            throw new EntityExistsException();
        }
    }

    public ProjectDto removeClient(int projectId) {
        if (repository.existsByProjectId(projectId)) {
            Project project = repository.findById(projectId).get();
            project.setClient(null);
            return (repository.save(project).toDto());
        } else {
            throw new EntityExistsException();
        }
    }
}
