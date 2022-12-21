package com.gokhan.engcompany.dto;

import com.gokhan.engcompany.entity.Person;

public class ClientDto {

    public int clientId;
    public PersonDto personDto;
    public String message = "Success";

    public ClientDto (String message) {
        this.message = message;
    }

    public ClientDto () {
    }

}
