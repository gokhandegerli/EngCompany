package com.gokhan.engcompany.entity;


import com.gokhan.engcompany.dto.DepartmentDto;
import com.gokhan.engcompany.dto.EmployeeDto;
import com.gokhan.engcompany.dto.ManagerDto;
import com.gokhan.engcompany.enums.DepartmentType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Department {

    private final static String MAP_CAT = "department";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int departmentId;

    @OneToOne(cascade = CascadeType.ALL)
    private Manager manager;

    @OneToMany(mappedBy = MAP_CAT, cascade = CascadeType.ALL)
    private List<Employee> employeeList = new ArrayList<>();

    @OneToMany(mappedBy = MAP_CAT, cascade = CascadeType.ALL)
    private List<Project> projectList = new ArrayList<>();

    @ManyToOne
    private HeadDepartment headDepartment;

    @Enumerated(EnumType.STRING)
    private DepartmentType departmentType;

    public Department() {
    }

    public Department(int departmentId, Manager manager, List<Employee> employeeList,
                      List<Project> projectList, HeadDepartment headDepartment, DepartmentType departmentType) {
        this.departmentId = departmentId;
        this.manager = manager;
        this.employeeList = employeeList;
        this.projectList = projectList;
        this.headDepartment = headDepartment;
        this.departmentType = departmentType;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public Manager getManager() {
        return manager == null
                ? null
                : manager; //If else'in kısa hali
    }

    public ManagerDto getManagerDto() {
        return manager == null
                ? null
                : manager.toDto(); //If else'in kısa hali
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    public DepartmentType getDepartmentType() {
        return departmentType;
    }

    public void setDepartmentType(DepartmentType departmentName) {
        this.departmentType = departmentName;
    }

    public HeadDepartment getGroup() {
        return headDepartment;
    }

    public void setGroup(HeadDepartment headDepartment) {
        this.headDepartment = headDepartment;
    }

    public HeadDepartment getHeadDepartment() {
        return headDepartment;
    }

    public void setHeadDepartment(HeadDepartment headDepartment) {
        this.headDepartment = headDepartment;
    }

    public DepartmentDto getDepartmentDto() {
        DepartmentDto dto = new DepartmentDto();
        dto.departmentIdDto = this.getDepartmentId();
        dto.departmentTypeDto = this.getDepartmentType();
        dto.managerDto = this.getManagerDto();
        dto.employeeDtoList = this.employeeList.stream().map(Employee::toDto).toList();
        dto.projectDtoList = this.projectList.stream().map(Project::toDto).toList();
        return dto;
    }
}
