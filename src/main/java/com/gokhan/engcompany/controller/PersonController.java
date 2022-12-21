package com.gokhan.engcompany.controller;


import com.gokhan.engcompany.dto.EmployeeDto;
import com.gokhan.engcompany.dto.PersonDto;
import com.gokhan.engcompany.request.EmployeeRequest;
import com.gokhan.engcompany.request.PersonRequest;
import com.gokhan.engcompany.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("persons")
public class PersonController {

    @Autowired
    PersonService service;

    @PutMapping("{personId}")
    public PersonDto updatePerson(@RequestBody PersonRequest personRequest,
                                  @PathVariable(value = "personId") int personId) {
        try {
            return service.updatePerson(personRequest, personId);
        }
        catch (EntityNotFoundException ex) {
            return new PersonDto("Person not exist!");
        }
    }

}