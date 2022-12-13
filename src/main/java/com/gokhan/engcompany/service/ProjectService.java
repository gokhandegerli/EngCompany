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

    protected boolean checkIfProjectExists(int projectId) {
        if (repository.existsByProjectId(projectId)) {
            return true;
        } else {
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
        List<Project> projectsInRange = new ArrayList<>();
        for (Project project : projectList) {
            if (project.getStartDate().isBefore(today.plusDays(dateRange))) {
                projectsInRange.add(project);
            }
        }
        return projectsInRange.stream().map(Project::toDto).toList();

       /* return projectList.stream() // alternatif, stream şeklinde olan.
                .filter(project -> project.getStartDate().isBefore(today.plusDays(dateRange)))
                .map(Project::toDto)
                .toList();*/
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
        return null;
    }

    public List<ProjectDto> getAllProjects() {
        return repository.findAll().stream().map(Project::toDto).toList();
    }


    public List<ProjectDto> getADepartmentProjects(int departmentId) {
        return repository.findByDepartmentDepartmentId(departmentId).stream()
                .map(Project::toDto)
                .toList();
    }

    public ProjectDto addEmployee(int employeeId, int projectId) {

        if (repository.existsByProjectId(projectId)){
            Project project = repository.findById(projectId).get();
            return (repository.save(checkIfEmployeeNotAssignedProject(project, employeeId))
                    .toDto());
        } else {
            throw new EntityExistsException();
        }
    }

    private Project checkIfEmployeeNotAssignedProject(Project project, int employeeId) {

        boolean alreadyExist = false;
        if(project.getEmployee() !=null) {
            alreadyExist = Integer.valueOf(project.getEmployee().getEmployeeId()).equals(employeeId);
        }
        if (alreadyExist) {
            throw new EntityExistsException();
        } else {
            Employee employee = employeeRepository.findByEmployeeId(employeeId).get();
            project.setEmployee(employee);
            return project;
        }

    }

    public ProjectDto removeEmployee(int employeeId, int projectId, boolean deleteEmployee) {

        if (repository.existsByProjectId(projectId)
                && employeeRepository.existsByEmployeeId(employeeId)) {
            Project project = repository.findById(projectId).get();
            project.setEmployee(null);
            if(deleteEmployee==true){
                employeeRepository.deleteById(employeeId);
            }
            return (repository.save(project).toDto());
        } else {
            throw new EntityExistsException();
        }
    }


    public ProjectDto addManager(int managerId, int projectId) {
        if (repository.existsByProjectId(projectId)){
            Project project = repository.findById(projectId).get();
            return (repository.save(checkIfManagerNotAssignedProject(project, managerId))
                    .toDto());
        } else {
            throw new EntityExistsException();
        }
    }

    private Project checkIfManagerNotAssignedProject(Project project, int managerId) {

        boolean alreadyExist = false;
        if(project.getManager() !=null) {
            alreadyExist = Integer.valueOf(project.getManager().getEmployeeId()).equals(managerId);
        }
        if (alreadyExist) {
            throw new EntityExistsException();
        } else {
            Employee manager = employeeRepository.findByEmployeeId(managerId).get();
            project.setManager(manager);
            return project;
        }
    }

    public ProjectDto removeManager(int managerId, int projectId, boolean deleteManager) {

        if (repository.existsByProjectId(projectId)
                && employeeRepository.existsByEmployeeId(managerId)) {
            Project project = repository.findById(projectId).get();
            project.setManager(null);
            if(deleteManager==true){
                employeeRepository.deleteById(managerId);
            }
            return (repository.save(project).toDto());
        } else {
            throw new EntityExistsException();
        }
    }


    public ProjectDto addClient(int clientId, int projectId) {
        if (repository.existsByProjectId(projectId)){
            Project project = repository.findById(projectId).get();
            return (repository.save(checkIfClientNotAssignedProject(project, clientId))
                    .toDto());
        } else {
            throw new EntityExistsException();
        }
    }

    private Project checkIfClientNotAssignedProject(Project project, int clientId) {
        boolean alreadyExist = false;
        if(project.getClient() !=null) {
            alreadyExist = Integer.valueOf(project.getClient().getClientId()).equals(clientId);
        }
        if (alreadyExist) {
            throw new EntityExistsException();
        } else {
            Client client = clientRepository.findByClientId(clientId).get();
            project.setClient(client);
            return project;
        }
    }

    public ProjectDto removeClient(int clientId, int projectId, boolean deleteClient) {
        if (repository.existsByProjectId(projectId)
                && clientRepository.existsById(clientId)) {
            Project project = repository.findById(projectId).get();
            project.setClient(null);
            if(deleteClient==true){
                clientRepository.deleteById(clientId);
            }
            return (repository.save(project).toDto());
        } else {
            throw new EntityExistsException();
        }
    }
}
