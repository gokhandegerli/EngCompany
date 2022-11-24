package com.gokhan.engcompany.entity;

import com.gokhan.engcompany.dto.ManagerDto;
import com.gokhan.engcompany.enums.Title;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
public class Manager {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int managerId;


    @Enumerated (EnumType.STRING)
    private Title title;

    @OneToOne (cascade = CascadeType.ALL)
    private Person person;

    public Manager() {
    }

    public Manager(int managerId, Title title, Person person) {
        this.managerId = managerId;
        this.title = title;
        this.person = person;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
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

    public ManagerDto toDto() {

            ManagerDto dto = new ManagerDto();
            dto.managerIdDto = this.getManagerId();
            dto.titleDto = this.getTitle();
            dto.personDto = this.person.toDto();
            return dto;

    }
}
