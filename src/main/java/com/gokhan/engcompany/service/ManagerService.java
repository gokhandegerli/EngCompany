package com.gokhan.engcompany.service;

import com.gokhan.engcompany.dto.ManagerDto;
import com.gokhan.engcompany.entity.Manager;
import com.gokhan.engcompany.entity.Person;
import com.gokhan.engcompany.repository.ManagerRepository;
import com.gokhan.engcompany.request.ManagerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;

@Service
public class ManagerService {

    @Autowired
    ManagerRepository repository;

    @Autowired
    PersonService personService;

    public Manager insertManager(ManagerRequest managerRequest) {
        Manager manager = new Manager();
        Person person = personService.insert(managerRequest.personRequest);
        manager.setPerson(person);
        manager.setTitle(managerRequest.title);
        return repository.save(manager);
    }

    public ManagerDto getManagerDto(int managerId) {

        return getManagerEntity(managerId).toDto();
    }



    public boolean checkManagerExist(int managerId) {
        if (repository.existsById(managerId)) {
            return true;
        } else {
            return false;
        }
    }

    public Manager getManagerEntity(int managerId) {
        return repository.findById(managerId).get();
    }
}
