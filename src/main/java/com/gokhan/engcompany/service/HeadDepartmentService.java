package com.gokhan.engcompany.service;

import com.gokhan.engcompany.dto.DepartmentDto;
import com.gokhan.engcompany.dto.HeadDepartmentDto;
import com.gokhan.engcompany.entity.Department;
import com.gokhan.engcompany.entity.Employee;
import com.gokhan.engcompany.entity.HeadDepartment;
import com.gokhan.engcompany.enums.DepartmentType;
import com.gokhan.engcompany.repository.HeadDepartmentRepository;
import com.gokhan.engcompany.request.HeadDepartmentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;

@Service
public class HeadDepartmentService {

    @Autowired
    HeadDepartmentRepository repository;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    DepartmentService departmentService;


    public HeadDepartmentDto createHeadDepartment(HeadDepartmentRequest headDepartmentRequest) {
        checkIfHeadDepartmentExists(headDepartmentRequest.departmentType);
        HeadDepartment headDepartment = new HeadDepartment();
        headDepartment.setDepartmentType(headDepartmentRequest.departmentType);
        repository.save(headDepartment);
        return headDepartment.toDto();
    }


    public HeadDepartmentDto updateManager(int employeeId, int headDepartmentId) {

        HeadDepartment headDepartment = repository.findById(headDepartmentId).get();
        Employee manager = employeeService.getManagerIfManager(employeeId);
        if (manager != null) {
            headDepartment.setManager(manager);
            manager.setManagerOf(headDepartmentId);
        } else { // custom throw add
            return null;
        }
        return repository.save(headDepartment).toDto();
    }

    public HeadDepartmentDto addDepartment(int departmentId, int headDepartmentId) {

        if (repository.existsByHeadDepartmentId(headDepartmentId).get()) {
            HeadDepartment headDepartment = repository.findById(headDepartmentId).get();
            return (repository.save(checkIfDepartmentNotAPartOfADepartmentList(headDepartment,departmentId))
                    .toDto());
        } else {
            throw new EntityExistsException();
        }

    }

    public HeadDepartment checkIfDepartmentNotAPartOfADepartmentList(HeadDepartment headDepartment,
                                                                     int departmentId) {
        if (headDepartment.getDepartmentList().stream().
                map(Department::getDepartmentId).equals(departmentId)) {
            throw new EntityExistsException();
        } else {
            Department department = departmentService.getDepartmentEntity(departmentId);
            department.setHeadDepartment(headDepartment);
            headDepartment.getDepartmentList().add(department);
            return  headDepartment;
        }
    }


    private void checkIfHeadDepartmentExists(DepartmentType departmentType) {
        if (repository.existsByDepartmentType(departmentType)) {
            throw new EntityExistsException();
        }
    }

    public HeadDepartment getHeadDepartmentEntity(int headDepartmentId) {

        HeadDepartment headDepartment = repository.findById(headDepartmentId).orElse(null);
        return headDepartment;
    }

    public String deleteHeadDepartment(int headDepartmentId) {

        if (getHeadDepartmentEntity(headDepartmentId) != null) {
            repository.deleteById(headDepartmentId);
            return "Bölüm silindi";
        } else {
            return "Bölüm silinemedi, çünkü yok";
        }
    }

    public HeadDepartmentDto getHeadDepartmentDto(int headDepartmentId) {

        HeadDepartment headDepartment = getHeadDepartmentEntity(headDepartmentId);
        if ( headDepartment == null) {
            return new HeadDepartmentDto("This Employee is not exist");
        } else {
            return headDepartment.toDto();
        }
    }
}
