package project_pet_backEnd.groomer.groomerleave.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.groomer.groomerleave.dto.request.ChangeLeaveReq;
import project_pet_backEnd.groomer.groomerleave.dto.request.InsertLeaveReq;
import project_pet_backEnd.groomer.groomerleave.dto.response.LeaveAllRes;
import project_pet_backEnd.groomer.groomerleave.service.GroomerLeaveService;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;
@Api(tags = "美容師請假功能")
@RestController
@Validated
public class GroomerLeaveController {

    @Autowired
    GroomerLeaveService groomerLeaveService;
    @ApiOperation("Man查詢請假單")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("hasAnyAuthority('美容師管理')")
    @GetMapping("/manager/findAllLeave")
    public ResultResponse<List<LeaveAllRes>> findAllLeave(){
        return groomerLeaveService.getAllLeave();
    }


    //審核請假單 (改變假單狀態) ，前端需提示修改預約單等。要注意為覆蓋班表(前端注意)。
    @ApiOperation("Man審核請假單")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("hasAnyAuthority('美容師管理')")
    @PostMapping("/manager/changeLeave")
    public ResultResponse<String> changeLeave(@RequestBody ChangeLeaveReq changeLeaveReq){
        return groomerLeaveService.changeLeave(changeLeaveReq);
    }

    //----------------------美容師個人管理--------------------//
    @ApiOperation("pg查詢自身請假單")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("hasAnyAuthority('美容師個人管理')")
    @GetMapping("/manager/getLeaveByPg")
    public ResultResponse<List<LeaveAllRes>> getLeaveByPg(@RequestAttribute(name = "managerId") Integer managerId){
        return groomerLeaveService.getLeaveForPg(managerId);
    }

    @ApiOperation("pg提交請假單")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("hasAnyAuthority('美容師管理')")
    @PostMapping("/manager/commitLeave")
    public ResultResponse<String> commitLeave(@RequestAttribute(name = "managerId") Integer managerId,
                                              @RequestBody InsertLeaveReq insertLeaveReq){
        return groomerLeaveService.insertLeaveForPg(managerId,insertLeaveReq);
    }

}
