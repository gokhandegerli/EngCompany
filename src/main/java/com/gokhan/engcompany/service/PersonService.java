package com.gokhan.engcompany.service;


import com.gokhan.engcompany.dto.PersonDto;
import com.gokhan.engcompany.entity.Person;
import com.gokhan.engcompany.repository.PersonRepository;
import com.gokhan.engcompany.request.PersonRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;

@Service
public class PersonService {

    @Autowired
    PersonRepository repository;

    public Person insert(PersonRequest personRequest) {

        checkPersonExist(personRequest.identityNumber);
        Person person = new Person();
        person.setFirstName(personRequest.firstName);
        person.setLastName(personRequest.lastName);
        person.setIdentityNumber(personRequest.identityNumber);
        person.seteEmail(personRequest.eMail);
        person.setAddress(personRequest.address);
        person.setCompanyName(personRequest.companyName);
        return repository.save(person);
    }

    private void checkPersonExist(String identityNumber) {
        if (repository.existsByIdentityNumber(identityNumber)){
            throw new EntityExistsException();
        }
    }


    public PersonDto getPersonDto(Person person) {

        return person.toDto();
    }




}
