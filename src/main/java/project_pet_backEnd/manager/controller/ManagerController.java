package project_pet_backEnd.manager.controller;

import org.springframework.data.relational.core.sql.In;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.manager.dto.AdjustPermissionRequest;
import project_pet_backEnd.manager.dto.CreateManagerRequest;
import project_pet_backEnd.manager.dto.ManagerLoginRequest;
import project_pet_backEnd.manager.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import project_pet_backEnd.user.dto.ResultResponse;

import javax.validation.Valid;

@RequestMapping("/manager")
@RestController
public class ManagerController {
    @Autowired
    private ManagerService managerService;
    @PostMapping("/createManager")
    @PreAuthorize("hasAnyAuthority('管理員管理')")
    public ResponseEntity<ResultResponse> createManager(@RequestBody @Valid CreateManagerRequest createManagerRequest){
        ResultResponse rs =managerService.createManager(createManagerRequest);
        return  ResponseEntity.status(201).body(rs);
    }
    @PostMapping("/login")
    public ResponseEntity<ResultResponse> managerLogin(@RequestBody @Valid ManagerLoginRequest managerLoginRequest){
        ResultResponse rs =managerService.managerLogin(managerLoginRequest);
        return  ResponseEntity.status(200).body(rs);
    }
    @PostMapping("/adjustPermission")
    public  ResponseEntity<?> adjustPermission(@RequestBody @Valid AdjustPermissionRequest adjustPermissionRequest){
        ResultResponse rs =managerService.adjustPermission(adjustPermissionRequest);
        return  ResponseEntity.status(200).body(rs);
    }
    @GetMapping("/authorities")
    public  ResponseEntity<ResultResponse> getManagerAuthorities(@RequestAttribute Integer managerId){
        ResultResponse rs =managerService.getManagerAuthoritiesById(managerId);
        return  ResponseEntity.status(200).body(rs);
    }
}
