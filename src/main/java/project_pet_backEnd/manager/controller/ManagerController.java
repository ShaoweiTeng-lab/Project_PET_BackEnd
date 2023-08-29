package project_pet_backEnd.manager.controller;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.manager.dto.*;
import project_pet_backEnd.manager.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import project_pet_backEnd.utils.commonDto.ResponsePage;
import project_pet_backEnd.utils.commonDto.ResultResponse;
import project_pet_backEnd.utils.commonDto.Page;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;
@Slf4j
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
    public ResponseEntity<ResultResponse<String>> managerLogin(@RequestBody @Valid ManagerLoginRequest managerLoginRequest){
        ResultResponse rs =managerService.managerLogin(managerLoginRequest);
        return  ResponseEntity.status(200).body(rs);
    }
    /**
     * 新增管理員
     * */
    @ApiOperation("新增管理員")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/manageManager")
    @PreAuthorize("hasAnyAuthority('管理員管理')")
    public ResponseEntity<ResultResponse<String>> createManager(@RequestBody @Valid CreateManagerRequest createManagerRequest){
        ResultResponse rs =managerService.createManager(createManagerRequest);
        return  ResponseEntity.status(201).body(rs);
    }
    /**
     * 查詢管理員
     * */
    @ApiOperation("查詢管理員")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("hasAnyAuthority('管理員管理')")
    @GetMapping("/manageManager")
    public  ResultResponse<ResponsePage<List<ManagerQueryResponse>>> getManagers(@RequestParam(required = false) String search,
                                                                         @RequestParam(defaultValue = "1") @Min(1) Integer page,
                                                                         @RequestParam(defaultValue = "5") @Min(1) Integer size){
        ResultResponse rs =new ResultResponse();
        QueryManagerParameter queryManagerParameter =new QueryManagerParameter();
        queryManagerParameter.setSearch(search);
        queryManagerParameter.setSize(size);
        queryManagerParameter.setPage(page);
        ResponsePage<List<ManagerQueryResponse>> pgList =managerService.getManagers(queryManagerParameter);
        rs.setMessage(pgList);
        return  rs;
    }

    /**
     * 修改管理員資料
     * */
    @ApiOperation("修改管理員資料")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("hasAnyAuthority('管理員管理')")
    @PutMapping("/manageManager")
    public  ResponseEntity<ResultResponse<String>> adjustManager(@RequestBody @Valid AdjustManagerRequest adjustManagerRequest){
        ResultResponse rs =managerService.adjustManager(adjustManagerRequest);
        return  ResponseEntity.status(200).body(rs);
    }



    /**
     * 查詢自身管理員權限
     * */
    @ApiOperation("查詢自身管理員權限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/authorities")
    public  ResponseEntity<ResultResponse<QueryManagerAuthorities>> getAuthorities(@ApiParam(hidden = true)@RequestAttribute Integer managerId){
        ResultResponse rs =managerService.getManagerAuthoritiesById(managerId);
        return  ResponseEntity.status(200).body(rs);
    }

    /**
     * 查詢管理員權限(來自form表單 ，必須先查詢該管理員 ，再把管理員名稱透過參數呼叫此API)
     * */
    @ApiOperation("查詢管理員權限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("hasAnyAuthority('管理員管理')")
    @GetMapping("/manageManager/authorities")
    public  ResponseEntity<ResultResponse<QueryManagerAuthorities>> getManagerAuthoritiesByAccount(@RequestParam @NotBlank String managerAccount){
        ResultResponse rs =managerService.getManagerAuthoritiesByAccount(managerAccount);
        return  ResponseEntity.status(200).body(rs);
    }
    /**
     * 修改管理員權限
     * */
    @PreAuthorize("hasAnyAuthority('管理員管理')")
    @ApiOperation("修改管理員權限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PutMapping("/manageManager/authorities")
    public  ResponseEntity<ResultResponse<String>> adjustPermission(@RequestBody @Valid AdjustPermissionRequest adjustPermissionRequest){
        ResultResponse rs =managerService.adjustPermission(adjustPermissionRequest);
        return  ResponseEntity.status(200).body(rs);
    }

    @ApiOperation("查詢個人資訊")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/profile")
    public ResponseEntity<ResultResponse< ManagerProfileResponse>> managerProfile( @ApiParam(hidden = true) @RequestAttribute("managerId")Integer managerId){
        ManagerProfileResponse managerProfileResponse =managerService.getProfile(managerId);
        ResultResponse rs =new ResultResponse();
        rs.setMessage(managerProfileResponse);
        return  ResponseEntity.status(200).body(rs);
    }
    @ApiOperation("修改自身密碼")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PutMapping("/profile")
    public  ResponseEntity<ResultResponse<String>> adjustProfile(@ApiParam(hidden = true) @RequestAttribute("managerId")Integer managerId,
                                            @RequestBody @Valid ManagerAdjustProfileRequest managerAdjustProfileRequest){

        managerService.adjustProfile(managerId,managerAdjustProfileRequest);
        ResultResponse rs =new ResultResponse();
        rs.setMessage("修改成功");
        return  ResponseEntity.status(200).body(rs);
    }

}
