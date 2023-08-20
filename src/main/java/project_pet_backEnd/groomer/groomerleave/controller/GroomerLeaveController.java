package project_pet_backEnd.groomer.groomerleave.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
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




}
