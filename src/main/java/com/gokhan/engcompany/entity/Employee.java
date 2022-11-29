package com.gokhan.engcompany.entity;


import com.gokhan.engcompany.dto.EmployeeDto;
import com.gokhan.engcompany.enums.Title;

import javax.persistence.*;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int employeeId;

    @Column(nullable = false)
    private Title title;

    @OneToOne(cascade = CascadeType.ALL)
    private Person person;

    @ManyToOne
    private Department department;
    private boolean isManager;
    private Integer managerOf;

    //assign a project to an engineer
    private Integer employeeOfProject;


    public Employee(int employeeId, Title title, Person person, Department department, boolean isManager,
                    Integer managerOf, Integer employeeOfProject) {
        this.employeeId = employeeId;
        this.title = title;
        this.person = person;
        this.department = department;
        this.isManager = isManager;
        this.managerOf = managerOf;
        this.employeeOfProject = employeeOfProject;
    }

    public Employee() {
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Integer getManagerOf() {
        return managerOf;
    }

    public void setManagerOf(Integer managerOf) {
        this.managerOf = managerOf;
    }

    public Integer getEmployeeOfProject() {
        return employeeOfProject;
    }

    public void setEmployeeOfProject(Integer employeeOfProject) {
        this.employeeOfProject = employeeOfProject;
    }

    public boolean GetIsManager() {
        return isManager;
    }

    public void setIsManager(boolean manager) {
        isManager = manager;
    }

    public EmployeeDto toDto() {
        EmployeeDto dto = new EmployeeDto();
        dto.employeeIdDto = this.getEmployeeId();
        dto.titleDto = this.getTitle();
        dto.personDto = this.person.toDto();
        dto.isManager = this.isManager;
        dto.managerOf = this.managerOf;
        dto.employeeOfProject = this.employeeOfProject;
        return dto;
    }


}
