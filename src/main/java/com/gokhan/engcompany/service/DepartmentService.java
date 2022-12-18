package com.gokhan.engcompany.service;

import com.gokhan.engcompany.dto.DepartmentDto;
import com.gokhan.engcompany.dto.EmployeeDto;
import com.gokhan.engcompany.dto.ProjectDto;
import com.gokhan.engcompany.entity.Department;
import com.gokhan.engcompany.entity.Employee;
import com.gokhan.engcompany.entity.Project;
import com.gokhan.engcompany.enums.DepartmentType;
import com.gokhan.engcompany.repository.DepartmentRepository;
import com.gokhan.engcompany.request.DepartmentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {


    @Autowired
    DepartmentRepository repository;
    @Autowired
    ProjectService projectService;
    @Autowired
    EmployeeService employeeService;

    public Department getDepartmentEntity(int departmentId) {

        return repository.findById(departmentId).get();
    }

    public DepartmentDto createDepartment(DepartmentRequest departmentRequest) {
        checkIfDepartmentExistsWithName(departmentRequest.departmentType);
        Department department = new Department();
        department.setDepartmentType(departmentRequest.departmentType);
        department = repository.save(department);
        return department.toDto();
    }

    protected void checkIfDepartmentExistsWithName(DepartmentType departmentType) {
        if (repository.existsByDepartmentType(departmentType)) {
            throw new EntityExistsException();
        }
    }

    public String deleteDepartment(int departmentId) {
        if (getDepartmentEntity(departmentId) != null) {
            repository.deleteById(departmentId);
            return "Bölüm silindi";
        } else {
            return "Bölüm silinemedi, çünkü yok";
        }
    }

    public DepartmentDto updateManager(int employeeId, int departmentId) {
        Department department = repository.findById(departmentId).get();
        Employee manager = employeeService.getManagerIfManager(employeeId);
        if (manager != null) {
            department.setManager(manager);
            manager.setDepartment(department);
        } else { // to be added custom throw
            return null;
        }
        return repository.save(department).toDto();
    }

    public DepartmentDto addEmployee(int employeeId, int departmentId) {

        Optional<Department> department = repository.findById(departmentId);
        checkIfEmployeeNotAPartOfAEmployeeList(department
                .orElseThrow(() -> new NullPointerException()),employeeId);
        Employee employee = employeeService.getEmployeeEntity(employeeId);
        department.get().getEmployeeList().add(employee);
        employee.setDepartment(department.get());
        return repository.save(department.get()).toDto();
    }

    public void checkIfEmployeeNotAPartOfAEmployeeList(Department department, int employeeId) {
        //stream olarak yazılacak, Berkay'a hatırlat.

        boolean alreadyExist=false;
        if (department.getEmployeeList() !=null) {
            alreadyExist = department.getEmployeeList().stream().anyMatch(employee1 -> employee1.
                    getEmployeeId() == employeeId);
        }
        if (alreadyExist) {
            throw new EntityExistsException();
        }
    }

    public DepartmentDto addProject(int projectId, int departmentId) {

        Optional<Department> department = repository.findById(departmentId);
        checkIfProjectNotAPartOfAProjectList(department
                .orElseThrow(() -> new NullPointerException()),projectId);
        Project project = projectService.getProjectEntity(projectId);
        department.get().getProjectList().add(project);
        project.setDepartment(department.get());
        return repository.save(department.get()).toDto();
    }

    private void checkIfProjectNotAPartOfAProjectList(Department department, int projectId) {

        boolean alreadyExist=false;
        if (department.getProjectList() !=null) {
            alreadyExist = department.getProjectList().stream().anyMatch(project1 -> project1.
                    getProjectId() == projectId);
        }
        if (alreadyExist) {
            throw new EntityExistsException();
        }
    }

    public DepartmentDto getDepartmentDto(int departmentId) {

        Department department = getDepartmentEntity(departmentId);
        if (department == null) {
            return new DepartmentDto("This Employee is not exist");
        } else {
            return department.toDto();
        }
    }

    public DepartmentDto removeEmployee(int employeeId, int departmentId) {
        if (repository.existsByDepartmentId(departmentId)) {
            Department department = repository.findById(departmentId).get();
            employeeService.getEmployeeEntity(employeeId).setDepartment(null);
            department.getEmployeeList().removeIf(employee ->
                    employee.getEmployeeId()==employeeId);
            return (repository.save(department).toDto());
        } else {
            throw new EntityExistsException();
        }
    }

    public DepartmentDto removeManager(int managerId, int departmentId) {
        if (repository.existsByDepartmentId(departmentId)) {
            Department department = repository.findById(departmentId).get();
            department.getManager().setDepartment(null);
            department.setManager(null);
            department.getEmployeeList().removeIf(employee ->
                    employee.getEmployeeId()==managerId);
            return (repository.save(department).toDto());
        } else {
            throw new EntityExistsException();
        }
    }

    public DepartmentDto removeProject(int projectId, int departmentId) {
        if (repository.existsByDepartmentId(departmentId)) {
            Department department = repository.findById(departmentId).get();
            projectService.getProjectEntity(projectId).setDepartment(null);
            department.getProjectList().removeIf(project ->
                    project.getProjectId()==projectId);
            return (repository.save(department).toDto());
        } else {
            throw new EntityExistsException();
        }
    }

    public DepartmentDto updateDepartment(int departmentId, DepartmentRequest departmentRequest) {

        return repository.save(repository.findById(departmentId).map(department1 -> {
            department1.setDepartmentType(departmentRequest.departmentType);
            return (department1);
        } ).orElseThrow(() -> new NullPointerException())).toDto();
    }

    public List<EmployeeDto> getADepartmentEmployees(int departmentId) {
        return repository.findById(departmentId).get()
                .getEmployeeList().stream()
                .map(Employee::toDto)
                .toList();
    }

    public List<ProjectDto> getADepartmentProjects(int departmentId) {
        return repository.findById(departmentId).get().getProjectList()
                .stream()
                .map(Project::toDto)
                .toList();
    }
}
