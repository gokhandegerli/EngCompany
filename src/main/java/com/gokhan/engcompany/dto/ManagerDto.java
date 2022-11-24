package com.gokhan.engcompany.dto;

import com.gokhan.engcompany.entity.Person;
import com.gokhan.engcompany.enums.Title;
import com.gokhan.engcompany.service.PersonService;

public class ManagerDto {

    public int managerIdDto;
    public PersonDto personDto;
    public Title titleDto;

    public String message = "Success";

    public ManagerDto(String message) {
        this.message = message;
    }

    public ManagerDto () {

    }

}
