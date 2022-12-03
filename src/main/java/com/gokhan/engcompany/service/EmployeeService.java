package com.gokhan.engcompany.service;

import com.gokhan.engcompany.dto.EmployeeDto;
import com.gokhan.engcompany.entity.Employee;
import com.gokhan.engcompany.repository.EmployeeRepository;
import com.gokhan.engcompany.request.EmployeeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository repository;

    @Autowired
    PersonService personService;


    public Employee getManagerIfManager(int employeeId) {

        Employee manager = repository.findById(employeeId).get();

        if ((manager.setManager() == true)) {
            return manager;
        } else { // to be added custom throw
            return null;
        }
    }

    public Employee getEmployeeEntity(int employeeId) {
        return repository.findByEmployeeId(employeeId).get();
    }

    public EmployeeDto getEmployeeDto(int employeeId) {
        Employee employee = getEmployeeEntity(employeeId);
        if (employee == null) {
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
        employee.setManager(employeeRequest.isManager);
        employee = repository.save(employee);
        return employee.toDto();
    }

    public EmployeeDto promoteEmployee(EmployeeRequest employeeRequest, int employeeId) {

        Employee employee = getEmployeeEntity(employeeId);
        if (!employee.setManager()) {
            employee.setManager(employeeRequest.isManager);
            employee.setTitle(employeeRequest.title);
            return repository.save(employee).toDto();
        } else {
            return new EmployeeDto("FAILED, This Employee is already a Manager, reconstruct it!");
        }
    }

    public String deleteEmployee(int employeeId) {

        if (getEmployeeEntity(employeeId) != null) {
            if (getEmployeeEntity(employeeId).getDepartment() == null) {
                repository.deleteById(employeeId);
                return "Kişi silindi";
            } else {
                return "Kişiyi önce ilgili departmandan siliniz!";
            }
        } else {
            return "Kişi silinemedi, çünkü yok";
        }
    }

    public EmployeeDto updateEmployee(EmployeeRequest employeeRequest, int employeeId) {
        Employee employee = getEmployeeEntity(employeeId);
        employee.setPerson(personService.update(employeeRequest.personRequest, employee.getPerson().getPersonId()));
        if (employee.setManager() != employeeRequest.isManager && employee.setManager() == false) {
            promoteEmployee(employeeRequest, employeeId);
        } else {
            employee.setTitle(employeeRequest.title);
            employee.setManager(employeeRequest.isManager);
        }
        return repository.save(employee).toDto();
    }

    protected boolean checkIfEmployeeExists(int employeeId) {
        if (repository.existsByEmployeeId(employeeId)) {
            return true;
        } else {
            throw new EntityExistsException();
        }
    }

    public String deleteManager(int managerId) {
        if (getEmployeeEntity(managerId) != null) {
            if (getEmployeeEntity(managerId).getDepartment() == null) {
                repository.deleteById(managerId);
                return "Manager silindi";
            } else {
                return "Manager önce ilgili departmandan siliniz!";
            }
        } else {
            return "Manager silinemedi, çünkü yok";
        }
    }
}
