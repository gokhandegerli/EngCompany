package com.gokhan.engcompany.service;


import com.gokhan.engcompany.dto.PersonDto;
import com.gokhan.engcompany.entity.Department;
import com.gokhan.engcompany.entity.Person;
import com.gokhan.engcompany.repository.PersonRepository;
import com.gokhan.engcompany.request.PersonRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    PersonRepository repository;

    public Person insert(PersonRequest personRequest) {

        checkByPersonIdentityNumber(personRequest.identityNumber);
        Person person = new Person();
        return setPersonRequestToPerson(personRequest, person);
    }

    public PersonDto getPersonDto(Person person) {

        return person.toDto();
    }

    public void checkByPersonIdentityNumber(String identityNumber) {
        if (repository.existsByIdentityNumber(identityNumber)) {
            throw new EntityExistsException();
        }
    }

    public PersonDto updatePerson(PersonRequest personRequest, int personId) {

        return repository.findById(personId)
                .map(person1 -> {return setPersonRequestToPerson(personRequest, person1);})
                .map(Person::toDto)
                .orElseThrow(() -> new EntityNotFoundException("person not found!"));

        /*Person person = repository.findById(personId)
                .orElseThrow(() -> new EntityNotFoundException("person not found!"));
        return setPersonRequestToPerson(personRequest, person).toDto();*/
    }

    public Person setPersonRequestToPerson(PersonRequest personRequest, Person person) {
        person.setFirstName(personRequest.firstName);
        person.setLastName(personRequest.lastName);
        person.setIdentityNumber(personRequest.identityNumber);
        person.setEmail(personRequest.email);
        person.setAddress(personRequest.address);
        person.setCompanyName(personRequest.companyName);
        return repository.save(person);
    }


}
