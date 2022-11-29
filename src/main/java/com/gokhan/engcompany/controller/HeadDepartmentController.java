package com.gokhan.engcompany.controller;


import com.gokhan.engcompany.dto.HeadDepartmentDto;
import com.gokhan.engcompany.request.HeadDepartmentRequest;
import com.gokhan.engcompany.service.HeadDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;


@RestController
@RequestMapping("head-departments")
//@Api(value="Head Department api documentation")
public class HeadDepartmentController {

    @Autowired
    HeadDepartmentService service;

    @PostMapping("create-head-department-with-name")
    //@ApiOperation(value="New head department create")
    public HeadDepartmentDto createHeadDepartment(@RequestBody HeadDepartmentRequest headDepartmentRequest) {

        try {
            return service.createHeadDepartment(headDepartmentRequest);
        } catch (EntityExistsException ex) {
            return new HeadDepartmentDto("FAILED, This Head Department already exists!");
        }
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
            return new HeadDepartmentDto("FAILED, This department already exists!");
        }
    }

}
