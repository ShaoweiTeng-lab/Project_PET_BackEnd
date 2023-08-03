package project_pet_backEnd.manager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.manager.dto.*;
import project_pet_backEnd.manager.service.ManagerService;
import project_pet_backEnd.manager.service.imp.ManagerServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import project_pet_backEnd.user.dto.ResultResponse;
import project_pet_backEnd.utils.commonDto.Page;

import javax.validation.Valid;
import java.util.List;

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


    @GetMapping("/authorities")
    public  ResponseEntity<ResultResponse> getAuthorities(@RequestAttribute Integer managerId){
        ResultResponse rs =managerService.getManagerAuthoritiesById(managerId);
        return  ResponseEntity.status(200).body(rs);
    }
    @PreAuthorize("hasAnyAuthority('管理員管理')")
    @GetMapping("/manageManager")
    public  ResponseEntity<Page<List<ManagerQueryResponse>>> getManagers(@RequestParam(defaultValue = "5") Integer limit,
                                                                         @RequestParam(defaultValue = "0") Integer offset){
        QueryManagerParameter queryManagerParameter =new QueryManagerParameter();
        queryManagerParameter.setLimit(limit);
        queryManagerParameter.setOffset(offset);
        Page<List<ManagerQueryResponse>> rs =managerService.getManagers(queryManagerParameter);
        return  ResponseEntity.status(200).body(rs);
    }

    /**
     * 修改管理員資料
     * */
    @PreAuthorize("hasAnyAuthority('管理員管理')")
    @PostMapping("/adjustManager")
    public  ResponseEntity<?> adjustManager(@RequestBody @Valid AdjustManagerRequest adjustManagerRequest){
        ResultResponse rs =managerService.adjustManager(adjustManagerRequest);
        return  ResponseEntity.status(200).body(rs);
    }
    /**
     * 修改管理員權限
     * */
    @PreAuthorize("hasAnyAuthority('管理員管理')")
    @PostMapping("/adjustPermission")
    public  ResponseEntity<?> adjustPermission(@RequestBody @Valid AdjustPermissionRequest adjustPermissionRequest){
        ResultResponse rs =managerService.adjustPermission(adjustPermissionRequest);
        return  ResponseEntity.status(200).body(rs);
    }
}
