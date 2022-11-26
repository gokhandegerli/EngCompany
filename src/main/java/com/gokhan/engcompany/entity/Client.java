package com.gokhan.engcompany.entity;

import com.gokhan.engcompany.dto.ClientDto;

import javax.persistence.*;

@Entity
public class Client {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int clientId;

    @OneToOne(cascade = CascadeType.ALL)
    private Person person;


    public Client() {
    }

    public Client(int clientId, Person person) {
        this.clientId = clientId;
        this.person = person;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public ClientDto toDto() {

        ClientDto dto = new ClientDto();
        dto.clientId = this.clientId;
        dto.personDto = this.person.toDto();
        return dto;
    }

}
