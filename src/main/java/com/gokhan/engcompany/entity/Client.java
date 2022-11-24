package com.gokhan.engcompany.entity;

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
}
