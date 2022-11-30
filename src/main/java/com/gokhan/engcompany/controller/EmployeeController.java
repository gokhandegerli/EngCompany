package com.gokhan.engcompany.controller;


import com.gokhan.engcompany.dto.EmployeeDto;
import com.gokhan.engcompany.dto.ProjectDto;
import com.gokhan.engcompany.request.EmployeeRequest;
import com.gokhan.engcompany.request.ProjectRequest;
import com.gokhan.engcompany.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    @Autowired
    EmployeeService service;

    @PostMapping("create-employee")
    public EmployeeDto createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        return service.createEmployee(employeeRequest);
    }

    @PostMapping("create-manager")
    public EmployeeDto createManager(@RequestBody EmployeeRequest employeeRequest) {
        return service.createManager(employeeRequest);
    }

    @PostMapping("{employeeId}/promote-employee")
    public EmployeeDto promoteEmployee(@RequestBody EmployeeRequest employeeRequest,
                                       @PathVariable (value="employeeId") int employeeId) {
        return service.promoteEmployee(employeeRequest, employeeId);
    }

    @PutMapping("{employeeId}/update-employee")
    public EmployeeDto updateEmployee(@RequestBody EmployeeRequest employeeRequest,
                                      @PathVariable (value="employeeId") int employeeId) {
        return service.updateEmployee(employeeRequest, employeeId);
    }

    @GetMapping ("{employeeId}")
    public EmployeeDto getEmployee(@PathVariable (value="employeeId") int employeeId) {
        return service.getEmployeeDto(employeeId);
    }

    @DeleteMapping("{employeeId}")
    public String deleteEmployee(@PathVariable (value="employeeId") int employeeId) {
        return service.deleteEmployee(employeeId);
    }

}
