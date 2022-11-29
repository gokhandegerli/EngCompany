package com.gokhan.engcompany.service;

import com.gokhan.engcompany.dto.EmployeeDto;
import com.gokhan.engcompany.entity.Employee;
import com.gokhan.engcompany.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository repository;

    @Autowired
    PersonService personService;



    private EmployeeDto toDto(Employee employee) {

        EmployeeDto dto = new EmployeeDto();
        dto.employeeIdDto = employee.getEmployeeId();
        dto.titleDto = employee.getTitle();
        dto.personDto = personService.getPersonDto(employee.getPerson());

        return dto;
    }

    public EmployeeDto getEmployeeDto(Employee employee) {
        return toDto(employee);
    }

    public Employee getManagerIfManager(int employeeId) {

        Employee manager = repository.findById(employeeId).get();

        if ( manager.isManager() == true) {
            return manager;
        } else { // custom throw add
            return null;
        }
    }

    public Employee getEmployeeEntity(int employeeId) {
        return repository.findById(employeeId).get();
    }
}
