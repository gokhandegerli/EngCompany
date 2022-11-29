package com.gokhan.engcompany.service;

import com.gokhan.engcompany.dto.DepartmentDto;
import com.gokhan.engcompany.dto.HeadDepartmentDto;
import com.gokhan.engcompany.entity.Department;
import com.gokhan.engcompany.entity.Employee;
import com.gokhan.engcompany.entity.HeadDepartment;
import com.gokhan.engcompany.enums.DepartmentType;
import com.gokhan.engcompany.repository.DepartmentRepository;
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

    public DepartmentDto createDepartment(DepartmentType departmentType) {
        checkIfDepartmentExists(departmentType);
        Department department = new Department();
        department.setDepartmentType(departmentType);
        repository.save(department);
        return department.toDto();
    }

    private void checkIfDepartmentExists(DepartmentType departmentType) {
        if (repository.existsByDepartmentType(departmentType).get()) {
            throw new EntityExistsException();
        }

    }

    public void checkIfDepartmentAPartOfADepartmentList(Department department, List<Department> departmentList) {
        for (Department department1 : departmentList) {
            if (department1 == department) {
                throw new EntityExistsException();
            }
        }
    }

    public String deleteDepartment(int departmentId) {
        repository.deleteById(departmentId);
        return "Bölüm silindi";

    }

    public DepartmentDto getDepartment(int departmentId) {
        return repository.findById(departmentId).get().toDto();
    }


    public DepartmentDto updateManager(int employeeId, int departmentId) {
        Department department = repository.findById(departmentId).get();
        Employee manager = employeeService.getManagerIfManager(employeeId);
        if (manager != null) {
            department.setManager(manager);
            manager.setManagerOf(departmentId);
        } else { // custom throw add
            return null;
        }
        return repository.save(department).toDto();
    }

    public DepartmentDto addEmployee(int employeeId, int departmentId) {

        if (repository.existsByDepartmentId(departmentId).get()) {
            Department department = repository.findById(departmentId).get();
            return (repository.save(checkIfEmployeeNotAPartOfAEmployeeList(department,employeeId))
                    .toDto());
        } else {
            throw new EntityExistsException();
        }
    }

    public Department checkIfEmployeeNotAPartOfAEmployeeList(Department department, int employeeId) {
        if (department.getEmployeeList().stream().map(Employee::getEmployeeId).equals(employeeId)) {
            throw new EntityExistsException();
        } else {
            Employee employee = employeeService.getEmployeeEntity(employeeId);
            employee.setDepartment(department);
            department.getEmployeeList().add(employee);
            return  department;
        }
    }

}
