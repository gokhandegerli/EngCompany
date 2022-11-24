package com.gokhan.engcompany.controller;

import com.gokhan.engcompany.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("managers")
public class ManagerController {

    @Autowired
    ManagerService service;

    /*        try {
        return service.insertGroup(departmentType);
    } catch(
    EntityExistsException ex) {
        return new GroupDto("fail");
    }*/
}
