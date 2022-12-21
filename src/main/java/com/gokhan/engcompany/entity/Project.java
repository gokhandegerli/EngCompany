package com.gokhan.engcompany.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gokhan.engcompany.dto.ClientDto;
import com.gokhan.engcompany.dto.DepartmentDto;
import com.gokhan.engcompany.dto.EmployeeDto;
import com.gokhan.engcompany.dto.ProjectDto;
import com.gokhan.engcompany.enums.ProjectStatus;

import javax.persistence.*;
import java.time.LocalDate;

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
    private Employee manager;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(nullable = false)
    @Enumerated (EnumType.STRING)
    private ProjectStatus projectStatus;


    //@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private LocalDate startDate;

    public Project() {
    }

    public Project(int projectId, String name, Client client, Employee employee,
                   Employee manager, Department department, ProjectStatus projectStatus, LocalDate startDate) {
        this.projectId = projectId;
        this.name = name;
        this.client = client;
        this.employee = employee;
        this.manager = manager;
        this.department = department;
        this.projectStatus = projectStatus;
        this.startDate = startDate;
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

    public ClientDto getClientDto() {
        return client == null
                ? null
                : client.toDto(); //If else'in kısa hali
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Employee getEmployee() {
        if(employee!=null) {
            this.employee = employee;
        }
        return employee;
    }

    public EmployeeDto getEmployeeDto() {
        return employee == null
                ? null
                : employee.toDto(); //If else'in kısa hali
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getManager() {
        return manager;
    }

    public EmployeeDto getManagerDto() {
        return manager == null
                ? null
                : manager.toDto(); //If else'in kısa hali
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public Department getDepartment() {
        return department;
    }

    public DepartmentDto getDepartmentDto() {
        return department == null
                ? null
                : department.toDto(); //If else'in kısa hali
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public ProjectDto toDto() {

        ProjectDto dto = new ProjectDto();
        dto.projectIdDto = this.projectId;
        dto.nameDto = this.name;
        dto.employeeDto = this.getEmployeeDto();
        dto.managerDto = this.getManagerDto();
        dto.clientDto = this.getClientDto();
        //dto.departmentDto = this.getDepartmentDto();
        dto.projectStatus = this.projectStatus;
        dto.startDate = this.startDate;
        return dto;
    }
}