package com.gokhan.engcompany.service;

import com.gokhan.engcompany.dto.DepartmentDto;
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
        repository.save(department);
        return department.toDto();
    }

    protected void checkIfDepartmentExistsWithName(DepartmentType departmentType) {
        if (repository.existsByDepartmentType(departmentType)) {
            throw new EntityExistsException();
        }
    }

    protected boolean checkIfDepartmentExists(int departmentId) {
        if (repository.existsByDepartmentId(departmentId)) {
            return true;
        } else {
            throw new EntityExistsException();
        }
    }

    //this method carried out in Head Department ATM.
    public void checkIfDepartmentAPartOfADepartmentList(Department department, List<Department> departmentList) {
        if (departmentList.stream().
                map(Department::getDepartmentId).equals(department.getDepartmentId())) {
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

        if (repository.existsByDepartmentId(departmentId)) {
            Department department = repository.findById(departmentId).get();
            return (repository.save(checkIfEmployeeNotAPartOfAEmployeeList(department, employeeId))
                    .toDto());
        } else {
            throw new EntityExistsException();
        }
    }

    public Department checkIfEmployeeNotAPartOfAEmployeeList(Department department, int employeeId) {
        //stream olarak yazılacak, Berkay'a hatırlat.
        if (department.getEmployeeList().stream().map(Employee::getEmployeeId).equals(employeeId)) {
            throw new EntityExistsException();
        } else {
            Employee employee = employeeService.getEmployeeEntity(employeeId);
            employee.setDepartment(department);
            department.getEmployeeList().add(employee);
            return department;
        }
    }

    public DepartmentDto addProject(int projectId, int departmentId) {

        if (repository.existsByDepartmentId(departmentId)) {
            Department department = repository.findById(departmentId).get();
            return (repository.save(checkIfProjectNotAPartOfAProjectList(department, projectId)).
                    toDto());
        } else {
            throw new EntityExistsException();
        }
    }

    private Department checkIfProjectNotAPartOfAProjectList(Department department, int projectId) {

        if (department.getProjectList().stream().map(Project::getProjectId).equals(projectId)) {
            throw new EntityExistsException();
        } else {
            Project project = projectService.getProjectEntity(projectId);
            project.setDepartment(department);
            department.getProjectList().add(project);
            return department;
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
        if (repository.existsByDepartmentId(departmentId)
                && employeeService.checkIfEmployeeExists(employeeId)) {
            Department department = repository.findById(departmentId).get();
            department.getEmployeeList().remove(employeeId);
            return (department.toDto());
        } else {
            throw new EntityExistsException();
        }
    }

    public DepartmentDto removeProject(int projectId, int departmentId) {
        if (repository.existsByDepartmentId(departmentId)
                && projectService.checkIfProjectExists(projectId)) {
            Department department = repository.findById(departmentId).get();
            department.getProjectList().remove(projectId);
            return (department.toDto());
        } else {
            throw new EntityExistsException();
        }
    }
}
