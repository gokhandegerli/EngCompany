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

    public List<EmployeeDto> getEmployeeDtoList(List<Employee> employeeList) {
        List<EmployeeDto> employeeDtoList = new ArrayList<>();

        for (Employee employee:employeeList) {
            employeeDtoList.add(toDto(employee));
        }
        return employeeDtoList;
    }

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
}
