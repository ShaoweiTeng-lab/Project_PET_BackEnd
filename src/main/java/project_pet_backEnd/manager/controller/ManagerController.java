package project_pet_backEnd.manager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import project_pet_backEnd.manager.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/Manager")
@RestController
public class ManagerController {
    @Autowired
    private ManagerService managerService;

}
