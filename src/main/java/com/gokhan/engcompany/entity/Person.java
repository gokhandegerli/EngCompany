package com.gokhan.engcompany.entity;

import com.gokhan.engcompany.dto.PersonDto;

import javax.persistence.*;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int personId;
    @Column(nullable=false)
    private String firstName;
    @Column(nullable=false)
    private String lastName;
    @Column(nullable=false)
    private String identityNumber;
    @Column(nullable=false)
    private String email;
    private String address;
    private String companyName;


    public Person() {
    }

    public Person(int personId, String firstName, String lastName, String identityNumber, String email,
                  String address, String companyName) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.identityNumber = identityNumber;
        this.email = email;
        this.address = address;
        this.companyName = companyName;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getEmail() {
        return email;
    }

    public void seteEmail(String eMail) {
        this.email = eMail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public PersonDto toDto() {

        PersonDto dto = new PersonDto();
        dto.personIdDto = this.getPersonId();
        dto.firstNameDto = this.getFirstName();
        dto.lastNameDto = this.getLastName();
        dto.identityNoDto = this.getIdentityNumber();
        dto.eMailDto = this.getEmail();
        dto.addressDto = this.getAddress();
        dto.companyNameDto = this.getCompanyName();

        return dto;
    }

}

