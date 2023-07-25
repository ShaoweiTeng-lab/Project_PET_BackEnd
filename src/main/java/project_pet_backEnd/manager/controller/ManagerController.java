package project_pet_backEnd.manager.controller;

import project_pet_backEnd.manager.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManagerController {
    @Autowired
    private ManagerService managerService;

}
