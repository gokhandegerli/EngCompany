package com.gokhan.engcompany.service;

import com.gokhan.engcompany.dto.DepartmentDto;
import com.gokhan.engcompany.entity.Department;
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
    ManagerService managerService;

    @Autowired
    ProjectService projectService;

    @Autowired
    EmployeeService employeeService;

    /*public List<DepartmentDto> getDepartmentDtoList(List<Department> departmentList) {
        List<DepartmentDto> departmentDtoList = new ArrayList<>();
        for(Department department:departmentList) {
            departmentDtoList.add(toDto(department));
        }
        return departmentDtoList;
    }*/

/*    private DepartmentDto toDto(Department department) {

        DepartmentDto dto = new DepartmentDto();
        dto.departmentIdDto = department.getDepartmentId();
        dto.departmentTypeDto = department.getDepartmentType();
        if (department.getManager() != null) {
            dto.managerDto = department.getManager().toDto();
        }
        dto.employeeDtoList = employeeService.getEmployeeDtoList(department.getEmployeeList());
        dto.projectDtoList = projectService.getProjectDtoList(department.getProjectList());
        return dto;
    }*/

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
        if (repository.existsByDepartmentType(departmentType)) {
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
}
