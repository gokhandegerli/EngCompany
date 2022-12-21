package com.gokhan.engcompany.dto;

public class PersonDto {

    public int personIdDto;
    public String firstNameDto;
    public String lastNameDto;
    public String identityNoDto;
    public String eMailDto;
    public String addressDto;
    public String companyNameDto;

    public String message = "Success";

    public PersonDto (String message) {
        this.message = message;
    }

    public PersonDto () {
    }

}
