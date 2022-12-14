package com.gokhan.engcompany.service;

import com.gokhan.engcompany.dto.EmployeeDto;
import com.gokhan.engcompany.entity.Employee;
import com.gokhan.engcompany.repository.EmployeeRepository;
import com.gokhan.engcompany.request.EmployeeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository repository;

    @Autowired
    PersonService personService;

    @Autowired
    ProjectService projectService;


    public Employee getManagerIfManager(int employeeId) {

        Employee manager = repository.findById(employeeId).get();

        if ((manager.getManager() == true)) {
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
        if (employee == null) {
            return new EmployeeDto("This Employee is not exist");
        } else {
            return employee.toDto();
        }
    }

    public EmployeeDto createEmployee(EmployeeRequest employeeRequest, boolean isManager) {

        Employee employee = new Employee();
        if (isManager) {
            employee.setManager(true);
        }
        employee.setPerson(personService.insert(employeeRequest.personRequest));
        employee.setTitle(employeeRequest.title);
        employee = repository.save(employee);
        return employee.toDto();
    }


    public EmployeeDto promoteEmployee(EmployeeRequest employeeRequest, int employeeId) {

        Employee employee = getEmployeeEntity(employeeId);
        if (!employee.getManager()) {
            employee.setManager(true);
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
                return "Ki??i silindi";
            } else {
                return "Ki??iyi ??nce ilgili departmandan siliniz!";
            }
        } else {
            return "Ki??i silinemedi, ????nk?? yok";
        }
    }

    public EmployeeDto updateEmployee(EmployeeRequest employeeRequest, int employeeId) {
        Employee employee = getEmployeeEntity(employeeId);
        //employee.setPerson(personService.update(employeeRequest.personRequest, employee.getPerson().getPersonId())); Person'a at PUT)
        employee.setTitle(employeeRequest.title);
        return repository.save(employee).toDto();
    }

    public List<EmployeeDto> getProjectFreeEmployees() {
        List<Employee> allEmployees = repository.findAll();
        List<Employee> assignedEmployees = projectService.getProjectWithEmployees(allEmployees);
        return allEmployees.stream()
                .filter(employee -> !assignedEmployees.contains(employee))
                .map(Employee::toDto) //forEach ile map fark??, biri void d??ner, biri ise tip d??ner.
                .toList();
    }

    public List<EmployeeDto> getAllEmployees() {
        return repository.findAll().stream().map(Employee::toDto).toList();
    }

}
