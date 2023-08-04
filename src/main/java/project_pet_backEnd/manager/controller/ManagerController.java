package project_pet_backEnd.manager.controller;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.manager.dto.*;
import project_pet_backEnd.manager.service.ManagerService;
import project_pet_backEnd.manager.service.imp.ManagerServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import project_pet_backEnd.user.dto.ResultResponse;
import project_pet_backEnd.utils.commonDto.Page;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Api(tags = "管理員管理")
@RequestMapping("/manager")
@RestController
@Validated
public class ManagerController {
    @Autowired
    private ManagerService managerService;
    /**
     * 管理員登入
     * */
    @ApiOperation("管理員登入")
    @PostMapping("/login")
    public ResponseEntity<ResultResponse> managerLogin(@RequestBody @Valid ManagerLoginRequest managerLoginRequest){
        ResultResponse rs =managerService.managerLogin(managerLoginRequest);
        return  ResponseEntity.status(200).body(rs);
    }
    /**
     * 新增管理員
     * */
    @ApiOperation("新增管理員")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT Token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/manageManager")
    @PreAuthorize("hasAnyAuthority('管理員管理')")
    public ResponseEntity<ResultResponse> createManager(@RequestBody @Valid CreateManagerRequest createManagerRequest){
        ResultResponse rs =managerService.createManager(createManagerRequest);
        return  ResponseEntity.status(201).body(rs);
    }
    /**
     * 查詢管理員
     * */
    @ApiOperation("查詢管理員")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT Token", required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("hasAnyAuthority('管理員管理')")
    @GetMapping("/manageManager")
    public  ResponseEntity<Page<List<ManagerQueryResponse>>> getManagers(@RequestParam(required = false) String search,
                                                                         @RequestParam(defaultValue = "5") Integer limit,
                                                                         @RequestParam(defaultValue = "0") Integer offset){
        QueryManagerParameter queryManagerParameter =new QueryManagerParameter();
        queryManagerParameter.setSearch(search);
        queryManagerParameter.setLimit(limit);
        queryManagerParameter.setOffset(offset);
        Page<List<ManagerQueryResponse>> rs =managerService.getManagers(queryManagerParameter);
        return  ResponseEntity.status(200).body(rs);
    }

    /**
     * 修改管理員資料
     * */
    @ApiOperation("修改管理員資料")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT Token", required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("hasAnyAuthority('管理員管理')")
    @PutMapping("/manageManager")
    public  ResponseEntity<?> adjustManager(@RequestBody @Valid AdjustManagerRequest adjustManagerRequest){
        ResultResponse rs =managerService.adjustManager(adjustManagerRequest);
        return  ResponseEntity.status(200).body(rs);
    }



    /**
     * 查詢自身管理員權限
     * */
    @ApiOperation("查詢自身管理員權限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/authorities")
    public  ResponseEntity<ResultResponse> getAuthorities(@ApiParam(hidden = true)@RequestAttribute Integer managerId){
        ResultResponse rs =managerService.getManagerAuthoritiesById(managerId);
        return  ResponseEntity.status(200).body(rs);
    }

    /**
     * 查詢管理員權限(來自form表單 ，必須先查詢該管理員 ，再把管理員名稱透過參數呼叫此API)
     * */
    @ApiOperation("查詢管理員權限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT Token", required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("hasAnyAuthority('管理員管理')")
    @GetMapping("/manageManager/authorities")
    public  ResponseEntity<ResultResponse> getManagerAuthoritiesByAccount(@RequestParam @NotBlank String managerAccount){
        ResultResponse rs =managerService.getManagerAuthoritiesByAccount(managerAccount);
        return  ResponseEntity.status(200).body(rs);
    }
    /**
     * 修改管理員權限
     * */
    @PreAuthorize("hasAnyAuthority('管理員管理')")
    @ApiOperation("修改管理員權限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT Token", required = true, dataType = "string", paramType = "header")
    })
    @PutMapping("/manageManager/authorities")
    public  ResponseEntity<?> adjustPermission(@RequestBody @Valid AdjustPermissionRequest adjustPermissionRequest){
        ResultResponse rs =managerService.adjustPermission(adjustPermissionRequest);
        return  ResponseEntity.status(200).body(rs);
    }



}
