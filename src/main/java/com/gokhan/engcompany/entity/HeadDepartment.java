package com.gokhan.engcompany.entity;


import com.gokhan.engcompany.dto.EmployeeDto;
import com.gokhan.engcompany.dto.HeadDepartmentDto;
import com.gokhan.engcompany.enums.DepartmentType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class HeadDepartment {

    private static final String MAP_CAT="headDepartment";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int headDepartmentId;

    @OneToOne (cascade = CascadeType.ALL)
    private Employee manager;

    @OneToMany (mappedBy = MAP_CAT,cascade = CascadeType.ALL)
    private List<Department> departmentList = new ArrayList<>();

    @Column(nullable = false)
    @Enumerated (EnumType.STRING)
    private DepartmentType departmentType;

    public HeadDepartment() {
    }

    public HeadDepartment(int headDepartmentId, Employee manager, List<Department> departmentList,
                          DepartmentType departmentType) {
        this.headDepartmentId = headDepartmentId;
        this.manager = manager;
        this.departmentList = departmentList;
        this.departmentType = departmentType;
    }

    public int getHeadDepartmentId() {
        return headDepartmentId;
    }

    public void setHeadDepartmentId(int headDepartmentId) {
        this.headDepartmentId = headDepartmentId;
    }

    public Employee getManager() {
        return manager ==  null
                ? null
                : manager;
    }

    public EmployeeDto getManagerDto() {
        return manager == null
                ? null
                : manager.toDto(); //If else'in kısa hali
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }

    public DepartmentType getDepartmentType() {
        return departmentType;
    }

    public void setDepartmentType(DepartmentType departmentTypeName) {
        this.departmentType = departmentTypeName;
    }

    public HeadDepartmentDto toDto() {
        HeadDepartmentDto dto = new HeadDepartmentDto();
        dto.headDepartmentIdDto = this.getHeadDepartmentId();
        dto.departmentType = this.getDepartmentType();
        dto.manager = this.getManagerDto();
        dto.departmentDtoList = this.getDepartmentList().stream().map(Department::toDto).toList();
        //dto.departmentDtoList = departmentService.getDepartmentDtoList(headDepartment.getDepartmentList());
        return dto;
    }

}

