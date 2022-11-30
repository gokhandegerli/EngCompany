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

    // with below field we have assigned an employee to a team
    @ManyToOne
    private Department department;
    private boolean isManager;
    private Integer managerOf;

    //assign a project to an engineer: OneToOne Engineer engineer field added to Project entity.



    public Employee(int employeeId, Title title, Person person, Department department, boolean isManager,
                    Integer managerOf) {
        this.employeeId = employeeId;
        this.title = title;
        this.person = person;
        this.department = department;
        this.isManager = isManager;
        this.managerOf = managerOf;
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


    public boolean isManager() {
        return isManager;
    }

    public void isManager(boolean manager) {
        isManager = manager;
    }

    public EmployeeDto toDto() {
        EmployeeDto dto = new EmployeeDto();
        dto.employeeIdDto = this.getEmployeeId();
        dto.titleDto = this.getTitle();
        dto.personDto = this.person.toDto();
        dto.isManager = this.isManager;
        dto.managerOf = this.managerOf;
        return dto;
    }


}
