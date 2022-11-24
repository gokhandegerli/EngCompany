package com.gokhan.engcompany.entity;

import com.gokhan.engcompany.dto.ProjectDto;

import javax.persistence.*;

@Entity
public class Project {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int projectId;

    @Column(nullable = false)
    private String name;

    @OneToOne
    private Client client;

    @OneToOne
    private Employee employee;

    @OneToOne
    private Manager manager;

    @ManyToOne
    private Department department;

    public Project() {
    }

    public Project(int projectId, String name, Client client, Employee employee,
                   Manager manager, Department department) {
        this.projectId = projectId;
        this.name = name;
        this.client = client;
        this.employee = employee;
        this.manager = manager;
        this.department = department;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public ProjectDto toDto() {

        ProjectDto dto = new ProjectDto();
        dto.projectIdDto = this.projectId;
        dto.nameDto = this.name;
        dto.employeeDto = this.employee.toDto();
        dto.managerDto = this.manager.toDto();
        dto.clientDto = this.client.toDto();
        return dto;
    }
}