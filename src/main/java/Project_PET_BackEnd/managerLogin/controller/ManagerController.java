package Project_PET_BackEnd.managerLogin.controller;

import Project_PET_BackEnd.managerLogin.service.ManagerService;
import Project_PET_BackEnd.userLogin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManagerController {
    @Autowired
    private ManagerService managerService;

}
