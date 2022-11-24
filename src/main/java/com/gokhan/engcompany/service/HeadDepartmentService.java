package com.gokhan.engcompany.service;

import com.gokhan.engcompany.dto.HeadDepartmentDto;
import com.gokhan.engcompany.entity.Department;
import com.gokhan.engcompany.entity.HeadDepartment;
import com.gokhan.engcompany.enums.DepartmentType;
import com.gokhan.engcompany.repository.HeadDepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;

@Service
public class HeadDepartmentService {

    @Autowired
    HeadDepartmentRepository repository;

    @Autowired
    ManagerService managerService;

    @Autowired
    DepartmentService departmentService;


    public HeadDepartmentDto createHeadDepartment(DepartmentType departmentType) {
        checkIfHeadDepartmentExists(departmentType);
        HeadDepartment headDepartment = new HeadDepartment();
        headDepartment.setDepartmentType(departmentType);
        repository.save(headDepartment);
        return toDto(headDepartment);
    }

    private HeadDepartmentDto toDto(HeadDepartment headDepartment) {
        HeadDepartmentDto dto = new HeadDepartmentDto();
        dto.headDepartmentIdDto = headDepartment.getHeadDepartmentId();
        dto.departmentType = headDepartment.getDepartmentType();
        if (headDepartment.getManager() != null) {
            dto.managerDto = headDepartment.getManager().toDto();
        }
        dto.departmentDtoList = departmentService.getDepartmentDtoList(headDepartment.getDepartmentList());
        return dto;
    }


    public HeadDepartmentDto addManager(int managerId, int headDepartmentId) {

        HeadDepartment headDepartment = repository.findById(headDepartmentId).get();
        headDepartment.setManager(managerService.getManagerEntity(managerId));
        return toDto(repository.save(headDepartment));
    }

    public HeadDepartmentDto addDepartment(int departmentId, int headDepartmentId) {

        HeadDepartment headDepartment = repository.findById(headDepartmentId).get();
        //if contains, stream: HeadDepartment sahip olduğu bütün departmanların id'leri toplanır, departmendId ile
        // contains sorgusu üzerinden karşılaştırılır, sonuca göre aşağıya devam edilir.
        Department department =departmentService.getDepartmentEntity(departmentId);
        departmentService.checkIfDepartmentAPartOfADepartmentList(department,headDepartment.getDepartmentList());
        //üstteki satır atıl
        department.setHeadDepartment(headDepartment);
        headDepartment.getDepartmentList().add(department);
        return toDto(repository.save(headDepartment));

    }

    private void checkIfHeadDepartmentExists(DepartmentType departmentType) {
        if (repository.existsByDepartmentType(departmentType)) {
            throw new EntityExistsException();
        }
    }

    public HeadDepartmentDto getHeadDepartmentDto(int headDepartmentId) {

        HeadDepartment headDepartment = repository.findById(headDepartmentId).get();
        return toDto(headDepartment);
    }

    public String deleteHeadDepartment(int headDepartmentId) {

        repository.deleteById(headDepartmentId);
        return "Bölüm silindi";
    }


}
