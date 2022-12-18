package com.gokhan.engcompany.controller;


import com.gokhan.engcompany.dto.DepartmentDto;
import com.gokhan.engcompany.dto.EmployeeDto;
import com.gokhan.engcompany.dto.HeadDepartmentDto;
import com.gokhan.engcompany.request.EmployeeRequest;
import com.gokhan.engcompany.request.HeadDepartmentRequest;
import com.gokhan.engcompany.service.HeadDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import java.util.List;


@RestController
@RequestMapping("head-departments")
public class HeadDepartmentController {

    @Autowired
    HeadDepartmentService service;

    @PostMapping()
    public HeadDepartmentDto createHeadDepartment(@RequestBody HeadDepartmentRequest headDepartmentRequest) {

        try {
            return service.createHeadDepartment(headDepartmentRequest);
        } catch (EntityExistsException ex) {
            return new HeadDepartmentDto("FAILED, This Head Department already exists!");
        }
    }

    @PutMapping("{headDepartmentId}")
    public HeadDepartmentDto updateHeadDepartment(@RequestBody HeadDepartmentRequest headDepartmentRequest,
                                      @PathVariable (value="headDepartmentId") int headDepartmentId) {
        return service.updateHeadDepartment(headDepartmentRequest, headDepartmentId);
    }

    @GetMapping("{headDepartmentId}")
    public HeadDepartmentDto getHeadDepartment(@PathVariable(name="headDepartmentId") int headDepartmentId) {
        return service.getHeadDepartmentDto(headDepartmentId);
    }

    @DeleteMapping("{headDepartmentId}")
    public String deleteHeadDepartment(@PathVariable(name="headDepartmentId") int headDepartmentId) {
        return service.deleteHeadDepartment(headDepartmentId);
    }

    @PutMapping("{headDepartmentId}/update-manager/{employeeId}")
    public HeadDepartmentDto updateManager(@PathVariable (value= "employeeId") int employeeId,
                                           @PathVariable (value="headDepartmentId") int headDepartmentId) {
        return service.updateManager(employeeId, headDepartmentId);
    }

    @PostMapping("{headDepartmentId}/add-department/{departmentId}")
    public HeadDepartmentDto addDepartment (@PathVariable (value="departmentId") int departmentId,
                                            @PathVariable (value="headDepartmentId") int headDepartmentId) {

        try {
            return service.addDepartment(departmentId, headDepartmentId);
        } catch (EntityExistsException ex) {
            return new HeadDepartmentDto("FAILED, This Department is  already in the list!");
        }
        catch (NullPointerException nex){
            return new HeadDepartmentDto("FAILED, Head Department not exist");
        }
    }

    @PostMapping("{headDepartmentId}/remove-department/{departmentId}")
    public HeadDepartmentDto removeDepartment (@PathVariable (value="departmentId") int departmentId,
                                        @PathVariable (value= "headDepartmentId") int headDepartmentId) {
        try {
            return service.removeDepartment(departmentId, headDepartmentId);
        } catch (EntityExistsException ex) {
            return new HeadDepartmentDto("FAILED, HeadDepartment not exist and/or" +
                    " this department is not a part of this HeadDepartment!");
        }
    }

    @GetMapping("/{headDepartmentId}/departments")
    public List<DepartmentDto> getDepartmentsOfHeadDepartment(@PathVariable(value = "headDepartmentId")
                                                              int headDepartmentId) {
        return service.getDepartmentsOfHeadDepartment(headDepartmentId);
    }

}
