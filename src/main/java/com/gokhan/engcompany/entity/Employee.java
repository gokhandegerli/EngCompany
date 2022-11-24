package com.gokhan.engcompany.entity;


import com.gokhan.engcompany.dto.EmployeeDto;
import com.gokhan.engcompany.enums.Title;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Employee {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int employeeId;

    @Column(nullable=false)
    private Title title;

    @OneToOne (cascade = CascadeType.ALL)
    private Person person;

    @ManyToOne
    private Department department;


    public Employee(int employeeId, Title title, Person person, Department department) {
        this.employeeId = employeeId;
        this.title = title;
        this.person = person;
        this.department = department;
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

    public EmployeeDto toDto() {

        EmployeeDto dto = new EmployeeDto();
        dto.employeeIdDto = this.getEmployeeId();
        dto.titleDto = this.getTitle();
        dto.personDto = this.person.toDto();
        return dto;
    }

    public List<EmployeeDto> getEmployeeDtoList(List<Employee> employeeList) {

    }

}
