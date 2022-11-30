package com.gokhan.engcompany.service;

import com.gokhan.engcompany.dto.EmployeeDto;
import com.gokhan.engcompany.entity.Employee;
import com.gokhan.engcompany.repository.EmployeeRepository;
import com.gokhan.engcompany.request.EmployeeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository repository;

    @Autowired
    PersonService personService;


    public Employee getManagerIfManager(int employeeId) {

        Employee manager = repository.findById(employeeId).get();

        if ((manager.isManager() == true)) {
            return manager;
        } else { // to be added custom throw
            return null;
        }
    }

    public Employee getEmployeeEntity(int employeeId) {
        return repository.findById(employeeId).get();
    }

    public EmployeeDto getEmployeeDto(int employeeId) {
        Employee employee = getEmployeeEntity(employeeId);
        if ( employee == null) {
            return new EmployeeDto("This Employee is not exist");
        } else {
            return employee.toDto();
        }
    }

    public EmployeeDto createEmployee(EmployeeRequest employeeRequest) {

        if (employeeRequest.isManager == false) {
            return createAndSetEmployee(employeeRequest);
        } else { //to be added custom throw
            return new EmployeeDto("FAILED, This Employee should not be set as Manager, reconstruct it!");
        }
    }


    public EmployeeDto createManager(EmployeeRequest employeeRequest) {

        if (employeeRequest.isManager == true) {
            return createAndSetEmployee(employeeRequest);
        } else { //to be added custom throw
            return new EmployeeDto("FAILED, This Employee should be set as Manager, reconstruct it!");
        }
    }

    public EmployeeDto createAndSetEmployee(EmployeeRequest employeeRequest) {
        //personService.checkByPersonIdentityNumber(employeeRequest.personRequest.identityNumber);
        Employee employee = new Employee();
        employee.setPerson(personService.insert(employeeRequest.personRequest));
        employee.setTitle(employeeRequest.title);
        employee.isManager(employeeRequest.isManager);
        repository.save(employee);
        return employee.toDto();
    }

    public EmployeeDto promoteEmployee(EmployeeRequest employeeRequest, int employeeId) {

        Employee employee = getEmployeeEntity(employeeId);
        if (!employee.isManager()) {
            employee.isManager(employeeRequest.isManager);
            employee.setTitle(employeeRequest.title);
            return repository.save(employee).toDto();
        } else {
            return new EmployeeDto("FAILED, This Employee is already a Manager, reconstruct it!");
        }
    }

    public String deleteEmployee(int employeeId) {

        if(getEmployeeEntity(employeeId)!= null) {
            repository.deleteById(employeeId);
            return "Kişi silindi";
        } else {
            return "Kişi silinemedi, çünkü yok";
        }
    }

    public EmployeeDto updateEmployee(EmployeeRequest employeeRequest, int employeeId) {
        Employee employee = getEmployeeEntity(employeeId);
        employee.setTitle(employeeRequest.title);
        employee.setPerson(personService.update(employeeRequest.personRequest, employee.getPerson().getPersonId()));
        employee.isManager(employeeRequest.isManager);
        return employee.toDto();
    }
}
