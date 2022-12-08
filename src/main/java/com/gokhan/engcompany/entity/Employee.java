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
    @Enumerated (EnumType.STRING)
    private Title title;

    @OneToOne(cascade = CascadeType.ALL)
    private Person person;

    // with below field we have assigned an employee to a team
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    private boolean isManager;


    //private Integer managerOf;

    //assign a project to an engineer: OneToOne Engineer engineer field added to Project entity.



    public Employee(int employeeId, Title title, Person person, Department department, boolean isManager) {
        this.employeeId = employeeId;
        this.title = title;
        this.person = person;
        this.department = department;
        this.isManager = isManager;

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
        if(title!=null) {
            this.title = title;
        }
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

    public boolean getManager() {
        return isManager;
    }

    public void setManager(boolean isManager) {
        this.isManager = isManager;
    }

    public EmployeeDto toDto() {
        EmployeeDto dto = new EmployeeDto();
        dto.employeeIdDto = this.getEmployeeId();
        dto.titleDto = this.getTitle();
        dto.personDto = this.person.toDto();
        dto.isManager = this.isManager;
        return dto;
    }


}
