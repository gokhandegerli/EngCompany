package com.gokhan.engcompany.controller;


import com.gokhan.engcompany.dto.HeadDepartmentDto;
import com.gokhan.engcompany.enums.DepartmentType;
import com.gokhan.engcompany.service.HeadDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;


@RestController
@RequestMapping("head-departments")
public class HeadDepartmentController {

    @Autowired
    HeadDepartmentService service;

    @PostMapping("create-head-department-with-name")
    public HeadDepartmentDto createHeadDepartment(@RequestBody DepartmentType departmentType) {

        try {
            return service.createHeadDepartment(departmentType);
        } catch (EntityExistsException ex) {
            return new HeadDepartmentDto("FAILED, This Head Department already exists!");
        }
    }

    @PutMapping("add-manager/{managerId}/{headDepartmentId}")
    public HeadDepartmentDto addManager (@PathVariable (value="managerId") int managerId,
                                                      @PathVariable (value="headDepartmentId") int headDepartmentId) {
        return service.addManager(managerId, headDepartmentId);
    }

    @PostMapping("add-department/{departmentId}/{headDepartmentId}")
    public HeadDepartmentDto addDepartment (@PathVariable (value="departmentId") int departmentId,
                                         @PathVariable (value="headDepartmentId") int headDepartmentId) {

        try {
            return service.addDepartment(departmentId, headDepartmentId);
        } catch (EntityExistsException ex) {
            return new HeadDepartmentDto("FAILED, This department already exists!");
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

}
