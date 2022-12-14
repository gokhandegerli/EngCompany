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
            //Employee employee = employeeService.getEmployeeEntity(employee);
            return (repository.save(checkIfEmployeeNotAPartOfAEmployeeList(department, employeeId))
                    .toDto());
        } else {
            throw new EntityExistsException();
        }
    }

    public Department checkIfEmployeeNotAPartOfAEmployeeList(Department department, int employeeId) {
        //stream olarak yazılacak, Berkay'a hatırlat.

        boolean alreadyExist = department.getEmployeeList().stream().anyMatch(employee1 -> Integer.valueOf(employee1.
                getEmployeeId()).equals(Integer.valueOf(employeeId)));
        if (alreadyExist) {
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

        boolean alreadyExist = department.getProjectList().stream().anyMatch(project1 -> Integer.valueOf(project1.
                getProjectId()).equals(Integer.valueOf(projectId)));

        //department.getProjectList().stream().map(Project::getProjectId).equals(projectId);

        if (alreadyExist) {
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

    public DepartmentDto removeEmployee(int employeeId, int departmentId, boolean deleteEmployee) {
        if (repository.existsByDepartmentId(departmentId)
                && employeeService.checkIfEmployeeExists(employeeId)) {
            Department department = repository.findById(departmentId).get();
            employeeService.getEmployeeEntity(employeeId).setDepartment(null);
            department.getEmployeeList().removeIf(employee ->
                    Integer.valueOf(employee.getEmployeeId()).equals(employeeId));
            if (deleteEmployee == true) {
                employeeService.deleteEmployee(employeeId);
            }
            return (repository.save(department).toDto());
        } else {
            throw new EntityExistsException();
        }
    }

    public DepartmentDto removeManager(int managerId, int departmentId, boolean deleteManager) {
        if (repository.existsByDepartmentId(departmentId)
                && employeeService.checkIfEmployeeExists(managerId)) {
            Department department = repository.findById(departmentId).get();
            department.getManager().setDepartment(null);
            department.setManager(null);
            department.getEmployeeList().removeIf(employee ->
                    Integer.valueOf(employee.getEmployeeId()).equals(managerId));
            if (deleteManager == true) {
                employeeService.deleteEmployee(managerId);
            }
            return (repository.save(department).toDto());
        } else {
            throw new EntityExistsException();
        }
    }

    public DepartmentDto removeProject(int projectId, int departmentId, boolean deleteProject) {
        if (repository.existsByDepartmentId(departmentId)
                && projectService.checkIfProjectExists(projectId)) {
            Department department = repository.findById(departmentId).get();
            projectService.getProjectEntity(projectId).setDepartment(null);
            department.getProjectList().removeIf(project ->
                    Integer.valueOf(project.getProjectId()).equals(projectId));
            if (deleteProject == true) {
                projectService.deleteProject(projectId);
            }
            return (repository.save(department).toDto());
        } else {
            throw new EntityExistsException();
        }
    }

    public DepartmentDto updateDepartment(int departmentId, DepartmentRequest departmentRequest) {

        Optional<Department> department = repository.findById(departmentId);
        if (department.isPresent()) {
            department.get().setDepartmentType(departmentRequest.departmentType);
            return repository.save(department.get()).toDto();
        }
        return null;
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
