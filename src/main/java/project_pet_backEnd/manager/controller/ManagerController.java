package project_pet_backEnd.manager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project_pet_backEnd.manager.dto.CreateManagerRequest;
import project_pet_backEnd.manager.dto.ManagerLoginRequest;
import project_pet_backEnd.manager.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import project_pet_backEnd.user.dto.loginResponse;

import javax.validation.Valid;

@RequestMapping("/manager")
@RestController
public class ManagerController {
    @Autowired
    private ManagerService managerService;
    @PostMapping("/createManager")
    @PreAuthorize("hasAnyAuthority('管理員管理')")
    public ResponseEntity<loginResponse> createManager(@RequestBody @Valid CreateManagerRequest createManagerRequest){
        return  ResponseEntity.status(201).body(null);
    }
    @PostMapping("/login")
    public ResponseEntity<loginResponse> managerLogin(@RequestBody @Valid ManagerLoginRequest managerLoginRequest){
        loginResponse rs =managerService.managerLogin(managerLoginRequest);
        return  ResponseEntity.status(200).body(rs);
    }
}
